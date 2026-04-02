package org.bukkit.craftbukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;

import com.legacyminecraft.poseidon.world.entity.EntityGiantZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Giant;

public class CraftGiant extends CraftMonster implements Giant {

    public CraftGiant(CraftServer server, EntityGiantZombie entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftGiant";
    }

}
