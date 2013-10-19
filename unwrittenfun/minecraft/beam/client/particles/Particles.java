package unwrittenfun.minecraft.beam.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public enum Particles {
    ITEM_BEAM;

    public void spawnParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ, int nBlocks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
            int particleSetting = mc.gameSettings.particleSetting;

            if (particleSetting == 2 || (particleSetting == 1 && world.rand.nextInt(3) == 0)) {
                return;
            }

//            double distanceX = mc.renderViewEntity.posX - x;
//            double distanceY = mc.renderViewEntity.posY - y;
//            double distanceZ = mc.renderViewEntity.posZ - z;
//
//            double maxDistance = 16;
//            if (distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ > maxDistance * maxDistance)
//                return;

            EntityFX particleEffect = null;
            switch(this) {
                case ITEM_BEAM:
                    particleEffect = new EntityItemBeamFX(world, x, y, z, motionX, motionY, motionZ, nBlocks);
                    break;
            }

            if (particleEffect != null) Minecraft.getMinecraft().effectRenderer.addEffect(particleEffect);
        }
    }
}
