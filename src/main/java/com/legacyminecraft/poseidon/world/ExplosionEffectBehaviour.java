package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Block;
import net.minecraft.server.MathHelper;

import java.util.Random;

/**
 * Canonical behaviour for explosion damage, fire ignition, and particle impulse math.
 */
public final class ExplosionEffectBehaviour {
    private static final ExplosionEffectBehaviour INSTANCE = new ExplosionEffectBehaviour();

    private ExplosionEffectBehaviour() {
    }

    public static ExplosionEffectBehaviour getInstance() {
        return INSTANCE;
    }

    public int computeEntityDamage(float explosionSize, double impactScale) {
        return (int) ((impactScale * impactScale + impactScale) / 2.0D * 8.0D * (double) explosionSize + 1.0D);
    }

    public boolean shouldIgniteBlock(int blockTypeId, int supportingBlockTypeId, Random random) {
        return blockTypeId == 0 && Block.o[supportingBlockTypeId] && random.nextInt(3) == 0;
    }

    public ParticleImpulse computeParticleImpulse(Random random,
                                                  int blockX,
                                                  int blockY,
                                                  int blockZ,
                                                  double explosionX,
                                                  double explosionY,
                                                  double explosionZ,
                                                  float explosionSize) {
        double sampleX = (float) blockX + random.nextFloat();
        double sampleY = (float) blockY + random.nextFloat();
        double sampleZ = (float) blockZ + random.nextFloat();
        double directionX = sampleX - explosionX;
        double directionY = sampleY - explosionY;
        double directionZ = sampleZ - explosionZ;
        double distance = MathHelper.a(directionX * directionX + directionY * directionY + directionZ * directionZ);

        if (distance == 0.0D) {
            return new ParticleImpulse(sampleX, sampleY, sampleZ, 0.0D, 0.0D, 0.0D);
        }

        directionX /= distance;
        directionY /= distance;
        directionZ /= distance;

        double impulseScale = 0.5D / (distance / (double) explosionSize + 0.1D);
        impulseScale *= random.nextFloat() * random.nextFloat() + 0.3F;

        directionX *= impulseScale;
        directionY *= impulseScale;
        directionZ *= impulseScale;

        return new ParticleImpulse(sampleX, sampleY, sampleZ, directionX, directionY, directionZ);
    }

    public static final class ParticleImpulse {
        private final double sampleX;
        private final double sampleY;
        private final double sampleZ;
        private final double motionX;
        private final double motionY;
        private final double motionZ;

        public ParticleImpulse(double sampleX, double sampleY, double sampleZ, double motionX, double motionY, double motionZ) {
            this.sampleX = sampleX;
            this.sampleY = sampleY;
            this.sampleZ = sampleZ;
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
        }

        public double getSampleX() {
            return sampleX;
        }

        public double getSampleY() {
            return sampleY;
        }

        public double getSampleZ() {
            return sampleZ;
        }

        public double getMotionX() {
            return motionX;
        }

        public double getMotionY() {
            return motionY;
        }

        public double getMotionZ() {
            return motionZ;
        }
    }
}
