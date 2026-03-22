package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityBoat;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

public final class BoatCollisionBehaviour {
    private static final BoatCollisionBehaviour INSTANCE = new BoatCollisionBehaviour();

    private BoatCollisionBehaviour() {
    }

    public static BoatCollisionBehaviour getInstance() {
        return INSTANCE;
    }

    public void collide(EntityBoat boat, Entity other) {
        org.bukkit.entity.Entity hitEntity = other == null ? null : other.getBukkitEntity();
        VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle) boat.getBukkitEntity(), hitEntity);
        boat.world.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            boat.poseidonSuperCollide(other);
        }
    }
}
