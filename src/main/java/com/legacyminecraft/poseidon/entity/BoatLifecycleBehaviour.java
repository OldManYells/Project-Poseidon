package com.legacyminecraft.poseidon.entity;


public final class BoatLifecycleBehaviour {
    private static final BoatLifecycleBehaviour INSTANCE = new BoatLifecycleBehaviour();

    private BoatLifecycleBehaviour() {
    }

    public static BoatLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean handleDamage(EntityBoat boat, Entity attacker, int damageAmount) {
        if (boat.world.isStatic || boat.dead) {
            return true;
        }

        Vehicle vehicle = (Vehicle) boat.getBukkitEntity();
        com.legacyminecraft.compat.bukkit.entity.Entity bukkitAttacker = attacker == null ? null : attacker.getBukkitEntity();
        VehicleDamageEvent event = new VehicleDamageEvent(vehicle, bukkitAttacker, damageAmount);
        boat.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return true;
        }

        boat.c = -boat.c;
        boat.b = 10;
        boat.damage += damageAmount * 10;
        boat.poseidonMarkDamaged();
        if (boat.damage > 40) {
            VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, bukkitAttacker);
            boat.world.getServer().getPluginManager().callEvent(destroyEvent);
            if (destroyEvent.isCancelled()) {
                boat.damage = 40;
                return true;
            }

            if (boat.passenger != null) {
                boat.passenger.mount(boat);
            }

            dropBoatMaterials(boat);
            boat.die();
        }

        return true;
    }

    public void dropBoatMaterials(EntityBoat boat) {
        for (int wood = 0; wood < 3; ++wood) {
            boat.a(Block.WOOD.id, 1, 0.0F);
        }
        for (int stick = 0; stick < 2; ++stick) {
            boat.a(Item.STICK.id, 1, 0.0F);
        }
    }

    public void updatePassengerPosition(EntityBoat boat) {
        if (boat.passenger == null) {
            return;
        }

        double offsetX = Math.cos((double) boat.yaw * 3.141592653589793D / 180.0D) * 0.4D;
        double offsetZ = Math.sin((double) boat.yaw * 3.141592653589793D / 180.0D) * 0.4D;
        boat.passenger.setPosition(
                boat.locX + offsetX,
                boat.locY + boat.poseidonPassengerYOffset() + boat.passenger.I(),
                boat.locZ + offsetZ
        );
    }

    public boolean interact(EntityBoat boat, EntityHuman player) {
        if (boat.passenger != null && boat.passenger instanceof EntityHuman && boat.passenger != player) {
            return true;
        }

        if (!boat.world.isStatic) {
            VehicleEnterEvent event = new VehicleEnterEvent((Vehicle) boat.getBukkitEntity(), player.getBukkitEntity());
            boat.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return true;
            }
            player.mount(boat);
        }

        return true;
    }
}
