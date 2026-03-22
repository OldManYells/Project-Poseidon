package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

/**
 * Canonical behaviour for CraftCreature target bridging and path assignment.
 */
public final class CreatureTargetBridgeBehaviour {
    private static final CreatureTargetBridgeBehaviour INSTANCE = new CreatureTargetBridgeBehaviour();

    private CreatureTargetBridgeBehaviour() {
    }

    public static CreatureTargetBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void setTarget(EntityCreature creature, LivingEntity target) {
        if (target == null) {
            creature.target = null;
            return;
        }

        if (target instanceof CraftLivingEntity) {
            EntityLiving targetEntity = ((CraftLivingEntity) target).getHandle();
            creature.target = targetEntity;
            creature.pathEntity = creature.world.findPath(creature, creature.target, 16.0F);
        }
    }

    public CraftLivingEntity getTarget(EntityCreature creature) {
        if (creature.target == null) {
            return null;
        }
        return (CraftLivingEntity) creature.target.getBukkitEntity();
    }
}
