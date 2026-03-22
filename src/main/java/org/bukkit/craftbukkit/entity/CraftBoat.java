package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.VehicleEntityPropertyBehaviour;
import net.minecraft.server.EntityBoat;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Boat;

public class CraftBoat extends CraftVehicle implements Boat {
    private static final VehicleEntityPropertyBehaviour VEHICLE_ENTITY_PROPERTY_BEHAVIOUR =
            VehicleEntityPropertyBehaviour.getInstance();
    protected EntityBoat boat;

    public CraftBoat(CraftServer server, EntityBoat entity) {
        super(server, entity);
        boat = entity;
    }

    public double getMaxSpeed() {
        return VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.getBoatMaxSpeed(boat);
    }

    public void setMaxSpeed(double speed) {
        VEHICLE_ENTITY_PROPERTY_BEHAVIOUR.setBoatMaxSpeed(boat, speed);
    }

    @Override
    public String toString() {
        return "CraftBoat";
    }
}
