package unwrittenfun.minecraft.beam.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class ModelBeam extends ModelBase {
    private ModelRenderer parts;

    public ModelBeam() {
        textureWidth = 128;
        textureHeight = 128;
        parts = new ModelRenderer(this);

        ModelRenderer rBase = new ModelRenderer(this, 0, 0);
        rBase.addBox(4, 0, 4, 24, 4, 24);
        rBase.setRotationPoint(-16, -16, -16);
        parts.addChild(rBase);

        ModelRenderer rBaseTop = new ModelRenderer(this, 0, 28);
        rBaseTop.addBox(6, 4, 6, 20, 4, 20);
        rBaseTop.setRotationPoint(-16, -16, -16);
        parts.addChild(rBaseTop);

        ModelRenderer rPole = new ModelRenderer(this, 96, 0);
        rPole.addBox(14, 4, 14, 4, 22, 4);
        rPole.setRotationPoint(-16, -16, -16);
        parts.addChild(rPole);

        ModelRenderer rRingTop = new ModelRenderer(this, 0, 52);
        rRingTop.addBox(10, 22, 10, 12, 2, 2);
        rRingTop.addBox(10, 22, 10, 2, 2, 12);
        rRingTop.addBox(20, 22, 10, 2, 2, 12);
        rRingTop.addBox(10, 22, 20, 12, 2, 2);
        rRingTop.setRotationPoint(-16, -16, -16);
        parts.addChild(rRingTop);

        ModelRenderer rRingBottom = new ModelRenderer(this, 0, 52);
        rRingBottom.addBox(8, 18, 8, 16, 2, 2);
        rRingBottom.addBox(8, 18, 8, 2, 2, 16);
        rRingBottom.addBox(22, 18, 8, 2, 2, 16);
        rRingBottom.addBox(8, 18, 22, 16, 2, 2);
        rRingBottom.setRotationPoint(-16, -16, -16);
        parts.addChild(rRingBottom);
    }

    public void render(float mult, int meta) {
        parts.rotateAngleZ = 0;
        parts.rotateAngleX = 0;

        switch (meta) {
            case 1:
                parts.rotateAngleX = (float) Math.PI;
                break;
            case 2:
                parts.rotateAngleX = (float) Math.PI * 1/2F;
                break;
            case 3:
                parts.rotateAngleX = (float) Math.PI * 3/2F;
                break;
            case 4:
                parts.rotateAngleZ = (float) Math.PI * 3/2F;
                break;
            case 5:
                parts.rotateAngleZ = (float) Math.PI * 1/2F;
                break;
        }
        parts.render(mult);
    }
}
