package unwrittenfun.minecraft.beam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import unwrittenfun.minecraft.beam.info.BlockInfo;
import unwrittenfun.minecraft.beam.info.ModInfo;
import unwrittenfun.minecraft.beam.tileentities.TEItemBeam;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class BlockItemBeam extends BlockContainer {
    public static Icon particleIcon;

    public BlockItemBeam(int id) {
        super(id, Material.iron);

        setHardness(2F);
        setStepSound(Block.soundMetalFootstep);
        setUnlocalizedName(BlockInfo.ITEM_BEAM_KEY);
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case 0:
                setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 0.8125F, 0.875F);
                break;
            case 1:
                setBlockBounds(0.125F, 0.1875F, 0.125F, 0.875F, 1F, 0.875F);
                break;
            case 2:
                setBlockBounds(0.125F, 0.125F, 0F, 0.875F, 0.875F, 0.8125F);
                break;
            case 3:
                setBlockBounds(0.125F, 0.125F, 0.1875F, 0.875F, 0.875F, 1F);
                break;
            case 4:
                setBlockBounds(0F, 0.125F, 0.125F, 0.8125F, 0.875F, 0.875F);
                break;
            case 5:
                setBlockBounds(0.1875F, 0.125F, 0.125F, 1F, 0.875F, 0.875F);
                break;
            default:
                setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon(ModInfo.TEXTURE_LOCATION + ":" + BlockInfo.ITEM_BEAM_KEY);
        particleIcon = register.registerIcon(ModInfo.TEXTURE_LOCATION + ":" + BlockInfo.ITEM_BEAM_KEY + "Particle");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TEItemBeam();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

            if (tileEntity instanceof TEItemBeam) {
                TEItemBeam beam = (TEItemBeam) tileEntity;

                if (beam.totalROff() != 0 ) {
                    beam.setReceiverOffset(0, 0, 0);
                    player.sendChatToPlayer(ChatMessageComponent.func_111066_d("Beam disconnected"));
                    return true;
                }

                if (beam.findReceivingBeam()) {
                    player.sendChatToPlayer(ChatMessageComponent.func_111066_d("Beam connected"));
                    return true;
                }

                player.sendChatToPlayer(ChatMessageComponent.func_111066_d("Could not locate beam"));
            }
        }

        return true;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.getOrientation(side).getOpposite().ordinal(), 3);

        return ForgeDirection.getOrientation(side).getOpposite().ordinal();
    }
}
