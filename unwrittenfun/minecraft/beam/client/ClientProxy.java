package unwrittenfun.minecraft.beam.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import unwrittenfun.minecraft.beam.CommonProxy;
import unwrittenfun.minecraft.beam.client.models.ModelBeam;
import unwrittenfun.minecraft.beam.client.renderers.RenderBeam;
import unwrittenfun.minecraft.beam.tileentities.TEItemBeam;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TEItemBeam.class, new RenderBeam(new ModelBeam()));
    }
}
