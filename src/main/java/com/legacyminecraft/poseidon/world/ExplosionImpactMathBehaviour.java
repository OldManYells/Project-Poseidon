package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.MathHelper;

/**
 * Canonical behaviour for explosion entity-impact direction/scale and knockback math.
 */
public final class ExplosionImpactMathBehaviour {
    private static final ExplosionImpactMathBehaviour INSTANCE = new ExplosionImpactMathBehaviour();

    private ExplosionImpactMathBehaviour() {
    }

    public static ExplosionImpactMathBehaviour getInstance() {
        return INSTANCE;
    }

    public ImpactComputation computeImpact(Entity entity,
                                           double explosionX,
                                           double explosionY,
                                           double explosionZ,
                                           float explosionSize,
                                           double blockDensity) {
        double normalizedDistance = entity.f(explosionX, explosionY, explosionZ) / (double) explosionSize;
        if (normalizedDistance > 1.0D) {
            return null;
        }

        double directionX = entity.locX - explosionX;
        double directionY = entity.locY - explosionY;
        double directionZ = entity.locZ - explosionZ;
        double directionLength = MathHelper.a(directionX * directionX + directionY * directionY + directionZ * directionZ);

        if (directionLength == 0.0D) {
            return null;
        }

        directionX /= directionLength;
        directionY /= directionLength;
        directionZ /= directionLength;
        double impactScale = (1.0D - normalizedDistance) * blockDensity;

        return new ImpactComputation(directionX, directionY, directionZ, impactScale);
    }

    public void applyKnockback(Entity entity, ImpactComputation impact, boolean markVelocityChanged) {
        entity.motX += impact.directionX * impact.impactScale;
        entity.motY += impact.directionY * impact.impactScale;
        entity.motZ += impact.directionZ * impact.impactScale;
        if (markVelocityChanged) {
            entity.velocityChanged = true;
        }
    }

    public static final class ImpactComputation {
        private final double directionX;
        private final double directionY;
        private final double directionZ;
        private final double impactScale;

        public ImpactComputation(double directionX, double directionY, double directionZ, double impactScale) {
            this.directionX = directionX;
            this.directionY = directionY;
            this.directionZ = directionZ;
            this.impactScale = impactScale;
        }

        public double getImpactScale() {
            return impactScale;
        }
    }
}
