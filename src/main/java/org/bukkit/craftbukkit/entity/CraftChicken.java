package org.bukkit.craftbukkit.entity;

import com.legacy.minecraft.poseidon.EntityChicken;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Chicken;

public class CraftChicken extends CraftAnimals implements Chicken {

    public CraftChicken(CraftServer server, EntityChicken entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftChicken";
    }
}
