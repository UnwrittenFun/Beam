package unwrittenfun.minecraft.beam.helpers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class BlockHelper {
    public static void spawnItemInWorld(World world, int x, int y, int z, ItemStack stack) {
        float spawnX = x + world.rand.nextFloat();
        float spawnY = y + world.rand.nextFloat();
        float spawnZ = z + world.rand.nextFloat();

        EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);

        droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * 0.05F;
        droppedItem.motionY = (4 + world.rand.nextFloat()) * 0.05F;
        droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * 0.05F;

        world.spawnEntityInWorld(droppedItem);
    }
}
