package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftCreature target bridging and path assignment.
 */
public final class CreatureTargetBridgeBehaviour {
    private static final CreatureTargetBridgeBehaviour INSTANCE = new CreatureTargetBridgeBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE_BEHAVIOUR =
            EntityHandleBridgeBehaviour.getInstance();
    private static final EntityBukkitProjectionBridgeBehaviour ENTITY_BUKKIT_PROJECTION_BRIDGE_BEHAVIOUR =
            EntityBukkitProjectionBridgeBehaviour.getInstance();

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

        EntityLiving targetEntity = ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveLivingHandle(target);
        if (targetEntity == null) {
            return;
        }

        creature.target = targetEntity;
        creature.pathEntity = creature.world.findPath(creature, creature.target, 16.0F);
    }

    public CraftLivingEntity getTarget(EntityCreature creature) {
        if (!(creature.target instanceof EntityLiving)) {
            return null;
        }
        return ENTITY_BUKKIT_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftLivingEntity((EntityLiving) creature.target);
    }
}
