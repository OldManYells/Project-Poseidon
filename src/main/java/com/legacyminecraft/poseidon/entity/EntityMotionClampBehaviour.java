package com.legacyminecraft.poseidon.entity;

/**
 * Canonical behaviour for entity motion component sanity clamping during load.
 */
public final class EntityMotionClampBehaviour {
    private static final EntityMotionClampBehaviour INSTANCE = new EntityMotionClampBehaviour();

    private EntityMotionClampBehaviour() {
    }

    public static EntityMotionClampBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldClamp(boolean isVehicleEntity) {
        return !isVehicleEntity;
    }

    public double clampMotionComponent(double component, double maxMagnitude) {
        return Math.abs(component) > maxMagnitude ? 0.0D : component;
    }
}
