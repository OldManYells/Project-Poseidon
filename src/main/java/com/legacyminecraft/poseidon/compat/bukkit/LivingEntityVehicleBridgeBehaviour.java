package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityLiving;
import org.bukkit.entity.Vehicle;

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

    public boolean isInsideVehicle(EntityLiving livingEntity) {
        return livingEntity.vehicle != null;
    }

    public boolean leaveVehicle(EntityLiving livingEntity) {
        if (livingEntity.vehicle == null) {
            return false;
        }

        livingEntity.setPassengerOf(null);
        return true;
    }

    public Vehicle resolveVehicle(EntityLiving livingEntity) {
        if (livingEntity.vehicle == null) {
            return null;
        }

        org.bukkit.entity.Entity bukkitVehicle = livingEntity.vehicle.getBukkitEntity();
        if (bukkitVehicle instanceof Vehicle) {
            return (Vehicle) bukkitVehicle;
        }

        return null;
    }
}
