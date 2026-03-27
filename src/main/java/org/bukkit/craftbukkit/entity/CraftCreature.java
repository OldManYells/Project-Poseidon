package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CreatureTargetBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import net.minecraft.server.EntityCreature;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

public class CraftCreature extends CraftLivingEntity implements Creature {
    private static final CreatureTargetBridgeBehaviour CREATURE_TARGET_BRIDGE_BEHAVIOUR =
            CreatureTargetBridgeBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();

    public CraftCreature(CraftServer server, EntityCreature entity) {
        super(server, entity);
    }

    public void setTarget(LivingEntity target) {
        CREATURE_TARGET_BRIDGE_BEHAVIOUR.setTarget(getHandle(), target);
    }

    public CraftLivingEntity getTarget() {
        return CREATURE_TARGET_BRIDGE_BEHAVIOUR.getTarget(getHandle());
    }

    @Override
    public EntityCreature getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityCreature.class);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftCreature");
    }
}
