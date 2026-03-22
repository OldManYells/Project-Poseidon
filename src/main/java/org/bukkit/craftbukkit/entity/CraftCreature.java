package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.CreatureTargetBridgeBehaviour;
import net.minecraft.server.EntityCreature;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

public class CraftCreature extends CraftLivingEntity implements Creature {
    private static final CreatureTargetBridgeBehaviour CREATURE_TARGET_BRIDGE_BEHAVIOUR =
            CreatureTargetBridgeBehaviour.getInstance();

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
        return (EntityCreature) entity;
    }

    @Override
    public String toString() {
        return "CraftCreature";
    }
}
