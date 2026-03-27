package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import net.minecraft.server.EntityPainting;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftEntity implements Painting {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();

    public CraftPainting(CraftServer server, EntityPainting entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftPainting");
    }

}
