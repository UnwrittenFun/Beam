package unwrittenfun.minecraft.beam;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import unwrittenfun.minecraft.beam.blocks.BeamBlocks;
import unwrittenfun.minecraft.beam.handlers.ConfigHandler;
import unwrittenfun.minecraft.beam.handlers.PacketHandler;
import unwrittenfun.minecraft.beam.info.ModInfo;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL }, clientSideRequired = true, serverSideRequired = false,
        packetHandler = PacketHandler.class)
public class BeamMod {
    @Mod.Instance(ModInfo.ID)
    public static BeamMod instance;

    @SidedProxy(clientSide = "unwrittenfun.minecraft.beam.client.ClientProxy",
            serverSide = "unwrittenfun.minecraft.beam.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        BeamBlocks.registerBlocks();
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        BeamBlocks.registerNames();
        BeamBlocks.registerRecipes();
        BeamBlocks.registerTileEntities();
    }

    @Mod.EventHandler
    public void modsLoaded(FMLPostInitializationEvent event) {
    }
}

