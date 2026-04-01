package org.bukkit.craftbukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;

import com.legacyminecraft.poseidon.world.entity.EntityZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Zombie;

public class CraftZombie extends CraftMonster implements Zombie {

    public CraftZombie(CraftServer server, EntityZombie entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftZombie";
    }
}
