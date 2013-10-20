package unwrittenfun.minecraft.beam.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import unwrittenfun.minecraft.beam.blocks.BlockItemBeam;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class EntityItemBeamFX extends EntityFX {
    public EntityItemBeamFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ, int nBlocks) {
        super(world, x, y, z, motionX, motionY, motionZ);

        setParticleIcon(BlockItemBeam.particleIcon);
        particleMaxAge = 3 + (nBlocks * 5);
        particleGravity = 0;
        particleScale = 1;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        noClip = true;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
}
