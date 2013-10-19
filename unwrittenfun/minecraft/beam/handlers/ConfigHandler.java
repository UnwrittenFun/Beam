package unwrittenfun.minecraft.beam.handlers;

import net.minecraftforge.common.Configuration;
import unwrittenfun.minecraft.beam.info.BlockInfo;

import java.io.File;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class ConfigHandler {
    public static void init(File file) {
        Configuration config = new Configuration(file);

        config.load();

        BlockInfo.ITEM_BEAM_ID = config.getBlock(BlockInfo.ITEM_BEAM_KEY, BlockInfo.ITEM_BEAM_DEFAULT_ID).getInt();

        config.save();
    }
}