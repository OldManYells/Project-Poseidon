package com.legacyminecraft.compat.bukkit;


import java.util.Set;
import java.util.UUID;

/**
 * Canonical behaviour for CraftPlayer hide/show/canSee tracking and tracker-entry bridge policy.
 */
public final class PlayerVisibilityBridgeBehaviour {
    private static final PlayerVisibilityBridgeBehaviour INSTANCE = new PlayerVisibilityBridgeBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE_BEHAVIOUR =
            EntityHandleBridgeBehaviour.getInstance();
    private static final WorldServerProjectionBridgeBehaviour WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR =
            WorldServerProjectionBridgeBehaviour.getInstance();

    private PlayerVisibilityBridgeBehaviour() {
    }

    public static PlayerVisibilityBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void hidePlayer(Set<UUID> hiddenPlayers, Object observer, Object player) {
        hiddenPlayers.add(BridgeReflection.cast(BridgeReflection.invoke(player, "getUniqueId")));

        Object observerHandle = ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHandle(observer);
        Object hiddenPlayer = ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHandle(player);
        Object observerWorld = BridgeReflection.getField(observerHandle, "world");
        Object tracker = WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveEntityTracker(observerWorld);
        if (tracker == null) {
            return;
        }
        Object trackerMap = BridgeReflection.getField(tracker, "b");
        int hiddenPlayerId = ((Number) BridgeReflection.getField(hiddenPlayer, "id")).intValue();
        Object trackerEntry = BridgeReflection.invoke(trackerMap, "a", hiddenPlayerId);
        if (trackerEntry != null) {
            BridgeReflection.invoke(trackerEntry, "c", observerHandle);
        }
    }

    public void showPlayer(Set<UUID> hiddenPlayers, Object observer, Object player) {
        hiddenPlayers.remove(BridgeReflection.cast(BridgeReflection.invoke(player, "getUniqueId")));

        Object observerHandle = ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHandle(observer);
        Object shownPlayer = ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHandle(player);
        Object observerWorld = BridgeReflection.getField(observerHandle, "world");
        Object tracker = WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveEntityTracker(observerWorld);
        if (tracker == null) {
            return;
        }
        Object trackerMap = BridgeReflection.getField(tracker, "b");
        int shownPlayerId = ((Number) BridgeReflection.getField(shownPlayer, "id")).intValue();
        Object trackerEntry = BridgeReflection.invoke(trackerMap, "a", shownPlayerId);
        if (trackerEntry != null) {
            Object trackedPlayers = BridgeReflection.getField(trackerEntry, "trackedPlayers");
            boolean contains = Boolean.TRUE.equals(BridgeReflection.invoke(trackedPlayers, "contains", observerHandle));
            if (!contains) {
                BridgeReflection.invoke(trackerEntry, "b", observerHandle);
            }
        }
    }

    public boolean canSee(Set<UUID> hiddenPlayers, Object player) {
        UUID uniqueId = BridgeReflection.cast(BridgeReflection.invoke(player, "getUniqueId"));
        return !hiddenPlayers.contains(uniqueId);
    }
}
