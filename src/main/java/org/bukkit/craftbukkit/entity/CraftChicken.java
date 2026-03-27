package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CraftEntityIdentityBehaviour;
import net.minecraft.server.EntityChicken;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Chicken;

public class CraftChicken extends CraftAnimals implements Chicken {
    private static final CraftEntityIdentityBehaviour CRAFT_ENTITY_IDENTITY_BEHAVIOUR =
            CraftEntityIdentityBehaviour.getInstance();

    public CraftChicken(CraftServer server, EntityChicken entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return CRAFT_ENTITY_IDENTITY_BEHAVIOUR.toString(this);
    }
}
