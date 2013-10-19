package unwrittenfun.minecraft.beam.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import unwrittenfun.minecraft.beam.info.BlockInfo;
import unwrittenfun.minecraft.beam.tileentities.TEItemBeam;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class BeamBlocks {
    public static BlockItemBeam itemBeam;

    public static void registerBlocks() {
        itemBeam = new BlockItemBeam(BlockInfo.ITEM_BEAM_ID);

        GameRegistry.registerBlock(itemBeam, BlockInfo.ITEM_BEAM_KEY);
    }

    public static void registerNames() {
        LanguageRegistry.addName(itemBeam, BlockInfo.ITEM_BEAM_NAME);
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TEItemBeam.class, BlockInfo.ITEM_BEAM_TE_KEY);
    }

    public static void registerRecipes() {
        // TODO: Add recipes
    }
}
