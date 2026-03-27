package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftLivingEntity vehicle-state bridge operations.
 */
public final class LivingEntityVehicleBridgeBehaviour {
    private static final LivingEntityVehicleBridgeBehaviour INSTANCE = new LivingEntityVehicleBridgeBehaviour();

    private LivingEntityVehicleBridgeBehaviour() {
    }

    public static LivingEntityVehicleBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isInsideVehicle(Object livingEntity) {
        return BridgeReflection.getField(livingEntity, "vehicle") != null;
    }

    public boolean leaveVehicle(Object livingEntity) {
        if (BridgeReflection.getField(livingEntity, "vehicle") == null) {
            return false;
        }

        BridgeReflection.invoke(livingEntity, "setPassengerOf", null);
        return true;
    }

    public <T> T resolveVehicle(Object livingEntity) {
        Object vehicle = BridgeReflection.getField(livingEntity, "vehicle");
        if (vehicle == null) {
            return null;
        }
        return BridgeReflection.cast(BridgeReflection.invoke(vehicle, "getBukkitEntity"));
    }
}
