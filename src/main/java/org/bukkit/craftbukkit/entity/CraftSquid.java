package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import net.minecraft.server.EntitySquid;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Squid;

public class CraftSquid extends CraftWaterMob implements Squid {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();

    public CraftSquid(CraftServer server, EntitySquid entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftSquid");
    }

}
