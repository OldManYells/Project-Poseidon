package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge for CraftFish owner conversion between Bukkit and NMS types.
 */
public final class FishHookOwnerBridgeBehaviour {
    private static final FishHookOwnerBridgeBehaviour INSTANCE = new FishHookOwnerBridgeBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE_BEHAVIOUR =
            EntityHandleBridgeBehaviour.getInstance();

    private FishHookOwnerBridgeBehaviour() {
    }

    public static FishHookOwnerBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public LivingEntity toBukkitOwner(EntityHuman owner) {
        if (owner == null) {
            return null;
        }
        return (LivingEntity) owner.getBukkitEntity();
    }

    public EntityHuman toNmsOwner(LivingEntity shooter) {
        return ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHumanHandle(shooter);
    }
}
