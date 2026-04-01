package org.bukkit.craftbukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Vehicle;

public abstract class CraftVehicle extends CraftEntity implements Vehicle {
    public CraftVehicle(CraftServer server, com.legacyminecraft.poseidon.world.entity.Entity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftVehicle{passenger=" + getPassenger() + '}';
    }
}
