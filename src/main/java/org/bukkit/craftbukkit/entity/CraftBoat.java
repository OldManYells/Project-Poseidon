package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.VehicleEntityPropertyBehaviour;
import net.minecraft.server.EntityBoat;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Boat;

public class CraftBoat extends CraftVehicle implements Boat {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final VehicleEntityPropertyBehaviour VEHICLE_ENTITY_PROPERTY_BEHAVIOUR =
            VehicleEntityPropertyBehaviour.getInstance();

    public CraftBoat(CraftServer server, EntityBoat entity) {
        super(server, entity);
    }

    public double getMaxSpeed() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getBoatMaxSpeed(getBoatHandle());
    }

    public void setMaxSpeed(double speed) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setBoatMaxSpeed(getBoatHandle(), speed);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftBoat");
    }

    private EntityBoat getBoatHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityBoat.class);
    }
}
