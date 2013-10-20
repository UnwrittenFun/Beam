package unwrittenfun.minecraft.beam.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.beam.client.models.ModelBeam;
import unwrittenfun.minecraft.beam.info.ModInfo;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class RenderBeam extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.TEXTURE_LOCATION, "textures/models/beam.png");
    private ModelBeam model;

    public RenderBeam(ModelBeam model) {
        this.model = model;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.render(0.0625F, tileEntity.getBlockMetadata());

        GL11.glPopMatrix();
    }
}
