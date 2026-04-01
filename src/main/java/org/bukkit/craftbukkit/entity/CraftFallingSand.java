package org.bukkit.craftbukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;

import com.legacyminecraft.poseidon.world.entity.EntityFallingSand;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.FallingSand;

public class CraftFallingSand extends CraftEntity implements FallingSand {

    public CraftFallingSand(CraftServer server, EntityFallingSand entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftFallingSand";
    }
}
