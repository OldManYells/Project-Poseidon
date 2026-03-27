package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntityPig;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Pig;

public class CraftPig extends CraftAnimals implements Pig {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftPig(CraftServer server, EntityPig entity) {
        super(server, entity);
    }

    public boolean hasSaddle() {
        return MOB_PROPERTY_BEHAVIOUR.hasPigSaddle(getHandle());
    }

    public void setSaddle(boolean saddled) {
        MOB_PROPERTY_BEHAVIOUR.setPigSaddle(getHandle(), saddled);
    }

    public EntityPig getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(super.getHandle(), EntityPig.class);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftPig");
    }
}
