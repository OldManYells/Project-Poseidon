package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.EntityGhast;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Ghast;

public class CraftGhast extends CraftFlying implements Ghast {

    public CraftGhast(CraftServer server, EntityGhast entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftGhast";
    }

}
