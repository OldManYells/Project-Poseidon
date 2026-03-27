package com.legacyminecraft.poseidon.entity;


public final class BoatCollisionBehaviour {
    private static final BoatCollisionBehaviour INSTANCE = new BoatCollisionBehaviour();

    private BoatCollisionBehaviour() {
    }

    public static BoatCollisionBehaviour getInstance() {
        return INSTANCE;
    }

    public void collide(EntityBoat boat, Entity other) {
        com.legacyminecraft.compat.bukkit.entity.Entity hitEntity = other == null ? null : other.getBukkitEntity();
        VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle) boat.getBukkitEntity(), hitEntity);
        boat.world.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            boat.poseidonSuperCollide(other);
        }
    }
}
