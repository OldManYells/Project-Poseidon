package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge behaviour for CraftLivingEntity world/source handle context projection.
 */
public final class LivingEntityContextBridgeBehaviour {
    private static final LivingEntityContextBridgeBehaviour INSTANCE = new LivingEntityContextBridgeBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE_BEHAVIOUR =
            EntityHandleBridgeBehaviour.getInstance();

    private LivingEntityContextBridgeBehaviour() {
    }

    public static LivingEntityContextBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveWorldHandle(Object world) {
        return WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world);
    }

    public <T> T resolveDamageSourceHandle(Object source) {
        return ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHandle(source);
    }
}
