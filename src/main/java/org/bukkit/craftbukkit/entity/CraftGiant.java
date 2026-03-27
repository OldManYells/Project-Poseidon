package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CraftEntityIdentityBehaviour;
import net.minecraft.server.EntityGiantZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Giant;

public class CraftGiant extends CraftMonster implements Giant {
    private static final CraftEntityIdentityBehaviour CRAFT_ENTITY_IDENTITY_BEHAVIOUR =
            CraftEntityIdentityBehaviour.getInstance();

    public CraftGiant(CraftServer server, EntityGiantZombie entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return CRAFT_ENTITY_IDENTITY_BEHAVIOUR.toString(this);
    }

}
