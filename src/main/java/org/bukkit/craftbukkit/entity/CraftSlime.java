package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntitySlime;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftLivingEntity implements Slime {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftSlime(CraftServer server, EntitySlime entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftSlime");
    }

    public EntitySlime getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(super.getHandle(), EntitySlime.class);
    }

    public int getSize() {
        return MOB_PROPERTY_BEHAVIOUR.getSlimeSize(getHandle());
    }

    public void setSize(int size) {
        MOB_PROPERTY_BEHAVIOUR.setSlimeSize(getHandle(), size);
    }
}
