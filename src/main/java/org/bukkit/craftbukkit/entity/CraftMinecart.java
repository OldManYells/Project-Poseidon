package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.VehicleEntityPropertyBehaviour;
import net.minecraft.server.EntityMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

public class CraftMinecart extends CraftVehicle implements Minecart {
    private static final VehicleEntityPropertyBehaviour VEHICLE_ENTITY_PROPERTY_BEHAVIOUR =
            VehicleEntityPropertyBehaviour.getInstance();
    /**
     * Stores the minecart type id, which is used by Minecraft to differentiate
     * minecart types. Here we use subclasses.
     */
    public enum Type {
        Minecart(0),
        StorageMinecart(1),
        PoweredMinecart(2);

        private final int id;

        private Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    protected EntityMinecart minecart;

    public CraftMinecart(CraftServer server, EntityMinecart entity) {
        super(server, entity);
        minecart = entity;
    }

    public void setDamage(int damage) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartDamage(minecart, damage);
    }

    public int getDamage() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartDamage(minecart);
    }

    public double getMaxSpeed() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartMaxSpeed(minecart);
    }

    public void setMaxSpeed(double speed) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartMaxSpeed(minecart, speed);
    }

    public boolean isSlowWhenEmpty() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.isMinecartSlowWhenEmpty(minecart);
    }

    public void setSlowWhenEmpty(boolean slow) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartSlowWhenEmpty(minecart, slow);
    }

    public Vector getFlyingVelocityMod() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartFlyingVelocityMod(minecart);
    }

    public void setFlyingVelocityMod(Vector flying) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartFlyingVelocityMod(minecart, flying);
    }

    public Vector getDerailedVelocityMod() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartDerailedVelocityMod(minecart);
    }

    public void setDerailedVelocityMod(Vector derailed) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartDerailedVelocityMod(minecart, derailed);
    }

    @Override
    public String toString() {
        return "CraftMinecart";
    }

}
