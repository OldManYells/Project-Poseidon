package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftBukkit vehicle wrapper property access and mutation.
 */
public final class VehicleEntityPropertyBehaviour {
    private static final VehicleEntityPropertyBehaviour INSTANCE = new VehicleEntityPropertyBehaviour();

    private VehicleEntityPropertyBehaviour() {
    }

    public static VehicleEntityPropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public double getBoatMaxSpeed(EntityBoat boat) {
        return boat.maxSpeed;
    }

    public void setBoatMaxSpeed(EntityBoat boat, double speed) {
        if (speed >= 0D) {
            boat.maxSpeed = speed;
        }
    }

    public int getMinecartDamage(EntityMinecart minecart) {
        return minecart.damage;
    }

    public void setMinecartDamage(EntityMinecart minecart, int damage) {
        minecart.damage = damage;
    }

    public double getMinecartMaxSpeed(EntityMinecart minecart) {
        return minecart.maxSpeed;
    }

    public void setMinecartMaxSpeed(EntityMinecart minecart, double speed) {
        if (speed >= 0D) {
            minecart.maxSpeed = speed;
        }
    }

    public boolean isMinecartSlowWhenEmpty(EntityMinecart minecart) {
        return minecart.slowWhenEmpty;
    }

    public void setMinecartSlowWhenEmpty(EntityMinecart minecart, boolean slowWhenEmpty) {
        minecart.slowWhenEmpty = slowWhenEmpty;
    }

    public Vector getMinecartFlyingVelocityMod(EntityMinecart minecart) {
        return new Vector(minecart.flyingX, minecart.flyingY, minecart.flyingZ);
    }

    public void setMinecartFlyingVelocityMod(EntityMinecart minecart, Vector flyingVelocityMod) {
        minecart.flyingX = flyingVelocityMod.getX();
        minecart.flyingY = flyingVelocityMod.getY();
        minecart.flyingZ = flyingVelocityMod.getZ();
    }

    public Vector getMinecartDerailedVelocityMod(EntityMinecart minecart) {
        return new Vector(minecart.derailedX, minecart.derailedY, minecart.derailedZ);
    }

    public void setMinecartDerailedVelocityMod(EntityMinecart minecart, Vector derailedVelocityMod) {
        minecart.derailedX = derailedVelocityMod.getX();
        minecart.derailedY = derailedVelocityMod.getY();
        minecart.derailedZ = derailedVelocityMod.getZ();
    }
}

