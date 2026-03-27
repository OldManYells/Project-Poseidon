package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.VehicleEntityPropertyBehaviour;
import net.minecraft.server.EntityMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

public class CraftMinecart extends CraftVehicle implements Minecart {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
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

    public CraftMinecart(CraftServer server, EntityMinecart entity) {
        super(server, entity);
    }

    public void setDamage(int damage) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartDamage(getMinecartHandle(), damage);
    }

    public int getDamage() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartDamage(getMinecartHandle());
    }

    public double getMaxSpeed() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartMaxSpeed(getMinecartHandle());
    }

    public void setMaxSpeed(double speed) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartMaxSpeed(getMinecartHandle(), speed);
    }

    public boolean isSlowWhenEmpty() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.isMinecartSlowWhenEmpty(getMinecartHandle());
    }

    public void setSlowWhenEmpty(boolean slow) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartSlowWhenEmpty(getMinecartHandle(), slow);
    }

    public Vector getFlyingVelocityMod() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartFlyingVelocityMod(getMinecartHandle());
    }

    public void setFlyingVelocityMod(Vector flying) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartFlyingVelocityMod(getMinecartHandle(), flying);
    }

    public Vector getDerailedVelocityMod() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getMinecartDerailedVelocityMod(getMinecartHandle());
    }

    public void setDerailedVelocityMod(Vector derailed) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setMinecartDerailedVelocityMod(getMinecartHandle(), derailed);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftMinecart");
    }

    private EntityMinecart getMinecartHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityMinecart.class);
    }
}
