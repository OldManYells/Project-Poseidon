package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import net.minecraft.server.EntityFallingSand;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.FallingSand;

public class CraftFallingSand extends CraftEntity implements FallingSand {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();

    public CraftFallingSand(CraftServer server, EntityFallingSand entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftFallingSand");
    }
}
