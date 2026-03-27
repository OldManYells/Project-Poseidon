package com.legacyminecraft.poseidon.world;


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

    public ImpactComputation computeImpact(Object entity,
                                           double explosionX,
                                           double explosionY,
                                           double explosionZ,
                                           float explosionSize,
                                           double blockDensity) {
        double normalizedDistance = distanceFrom(entity, explosionX, explosionY, explosionZ) / (double) explosionSize;
        if (normalizedDistance > 1.0D) {
            return null;
        }

        double directionX = readDouble(entity, "locX") - explosionX;
        double directionY = readDouble(entity, "locY") - explosionY;
        double directionZ = readDouble(entity, "locZ") - explosionZ;
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

    public void applyKnockback(Object entity, ImpactComputation impact, boolean markVelocityChanged) {
        addDouble(entity, "motX", impact.directionX * impact.impactScale);
        addDouble(entity, "motY", impact.directionY * impact.impactScale);
        addDouble(entity, "motZ", impact.directionZ * impact.impactScale);
        if (markVelocityChanged) {
            writeBoolean(entity, "velocityChanged", true);
        }
    }

    private static double distanceFrom(Object entity, double x, double y, double z) {
        try {
            Object value = entity.getClass()
                    .getMethod("f", Double.TYPE, Double.TYPE, Double.TYPE)
                    .invoke(entity, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z));
            return value instanceof Double ? ((Double) value).doubleValue() : 0.0D;
        } catch (ReflectiveOperationException ignored) {
            double deltaX = readDouble(entity, "locX") - x;
            double deltaY = readDouble(entity, "locY") - y;
            double deltaZ = readDouble(entity, "locZ") - z;
            return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        }
    }

    private static double readDouble(Object target, String fieldName) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            return field.getDouble(target);
        } catch (ReflectiveOperationException ignored) {
            return 0.0D;
        }
    }

    private static void addDouble(Object target, String fieldName, double delta) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setDouble(target, field.getDouble(target) + delta);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private static void writeBoolean(Object target, String fieldName, boolean value) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setBoolean(target, value);
        } catch (ReflectiveOperationException ignored) {
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
