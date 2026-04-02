package org.bukkit.craftbukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;

import com.legacyminecraft.poseidon.world.entity.EntityWaterAnimal;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.WaterMob;

public class CraftWaterMob extends CraftCreature implements WaterMob {

    public CraftWaterMob(CraftServer server, EntityWaterAnimal entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftWaterMob";
    }

}
