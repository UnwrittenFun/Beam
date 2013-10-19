package unwrittenfun.minecraft.beam.tileentities;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import unwrittenfun.minecraft.beam.blocks.BeamBlocks;
import unwrittenfun.minecraft.beam.client.particles.Particles;
import unwrittenfun.minecraft.beam.handlers.PacketHandler;
import unwrittenfun.minecraft.beam.helpers.BlockHelper;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class TEItemBeam extends TileEntity {
    public int rOffX, rOffY, rOffZ;
    protected int delay;
    protected boolean loaded = false;

    public void setReceiverOffset(int offX, int offY, int offZ) {
        this.rOffX = offX;
        this.rOffY = offY;
        this.rOffZ = offZ;

        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) PacketHandler.sendROffChangePacket(this);
    }

    @Override
    public void updateEntity() {
        if (hasWorldObj()) {
            if (totalROff() != 0) {
                if (delay <= 0) {
                    delay = 5;

                    if (worldObj.isRemote) {
                        spawnParticle();
                    } else {
                        sendItem();
                    }
                }

                delay--;
            }

            if (!loaded && worldObj.isRemote) {
                loaded = true;
                PacketHandler.requestROffPacket(this);
            }
        }
    }

    public void spawnParticle() {
        ForgeDirection direction = getFacingDirection().getOpposite();
        double mx = direction.offsetX * 0.2F;
        double my = direction.offsetY * 0.2F;
        double mz = direction.offsetZ * 0.2F;

        Particles.ITEM_BEAM.spawnParticle(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, mx, my, mz,
                Math.abs(totalROff()) - 1);
    }

    public int totalROff() {
        return rOffX + rOffY + rOffZ;
    }

    public void sendItem() {
        TEItemBeam beam = getReceiverBeam();
        IInventory inventory = getAdjacentInventory();

        if (beam == null) {
            findReceivingBeam();
            return;
        }

        if (inventory != null) {
            if (inventory instanceof ISidedInventory) {
                ISidedInventory sidedInventory = ((ISidedInventory) inventory);
                int[] slots = sidedInventory.getAccessibleSlotsFromSide(getFacingDirection().getOpposite().ordinal());

                for (int slotIndex : slots) {
                    ItemStack stack = sidedInventory.getStackInSlot(slotIndex);

                    if (stack != null &&
                        sidedInventory.canExtractItem(slotIndex, stack, getFacingDirection().getOpposite().ordinal())) {
                        beam.receiveItem(sidedInventory.decrStackSize(slotIndex, 1));
                        break;
                    }
                }
            } else {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);

                    if (stack != null) {
                        beam.receiveItem(inventory.decrStackSize(i, 1));
                        break;
                    }
                }
            }

        }
    }

    public void receiveItem(ItemStack newStack) {
        IInventory inventory = getAdjacentInventory();

        if (inventory != null) {
            boolean success = false;

            if (inventory instanceof ISidedInventory) {
                ISidedInventory sidedInventory = ((ISidedInventory) inventory);
                int[] slots = sidedInventory.getAccessibleSlotsFromSide(getFacingDirection().getOpposite().ordinal());

                for (int slotIndex : slots) {
                    ItemStack stack = inventory.getStackInSlot(slotIndex);

                    if (sidedInventory.canInsertItem(slotIndex, stack, getFacingDirection().getOpposite().ordinal())) {
                        if (stack == null) {
                            inventory.setInventorySlotContents(slotIndex, newStack);
                            success = true;
                            break;
                        } else if (stack.isItemEqual(newStack) && stack.stackSize < stack.getMaxStackSize()) {
                            stack.stackSize++;
                            inventory.setInventorySlotContents(slotIndex, stack);
                            success = true;
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);

                    if (stack == null) {
                        inventory.setInventorySlotContents(i, newStack);
                        success = true;
                        break;
                    } else if (stack.isItemEqual(newStack) && stack.stackSize < stack.getMaxStackSize()) {
                        stack.stackSize++;
                        inventory.setInventorySlotContents(i, stack);
                        success = true;
                        break;
                    }
                }
            }

            if (!success) {
                BlockHelper.spawnItemInWorld(worldObj, xCoord, yCoord, zCoord, newStack);
            }
        } else {
            BlockHelper.spawnItemInWorld(worldObj, xCoord, yCoord, zCoord, newStack);
        }
    }

    public TEItemBeam getReceiverBeam() {
        TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord + rOffX, yCoord + rOffY, zCoord + rOffZ);
        if (tileEntity instanceof TEItemBeam) return (TEItemBeam) tileEntity;

        return null;
    }

    public IInventory getAdjacentInventory() {
        ForgeDirection direction = getFacingDirection();

        TileEntity tileEntity = worldObj
                .getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if (tileEntity instanceof IInventory) return (IInventory) tileEntity;

        return null;
    }

    public boolean findReceivingBeam() {
        ForgeDirection direction = getFacingDirection().getOpposite();

        for (int da = 1; da <= 16; da++) {
            int x = xCoord + (direction.offsetX * da);
            int y = yCoord + (direction.offsetY * da);
            int z = zCoord + (direction.offsetZ * da);

            if (worldObj.getBlockId(x, y, z) == BeamBlocks.itemBeam.blockID &&
                worldObj.getBlockMetadata(x, y, z) != getBlockMetadata()) {
                setReceiverOffset(direction.offsetX * da, direction.offsetY * da, direction.offsetZ * da);
                return true;
            }
        }

        setReceiverOffset(0, 0, 0);
        return false;
    }

    public ForgeDirection getFacingDirection() {
        return ForgeDirection.getOrientation(getBlockMetadata());
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("rOffX", rOffX);
        compound.setInteger("rOffY", rOffY);
        compound.setInteger("rOffZ", rOffZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        rOffX = compound.getInteger("rOffX");
        rOffY = compound.getInteger("rOffY");
        rOffZ = compound.getInteger("rOffZ");
    }
}
