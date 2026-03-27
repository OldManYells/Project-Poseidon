package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftPlayer teleport event + world-transfer orchestration.
 */
public final class PlayerTeleportBridgeBehaviour {
    private static final PlayerTeleportBridgeBehaviour INSTANCE = new PlayerTeleportBridgeBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();

    private PlayerTeleportBridgeBehaviour() {
    }

    public static PlayerTeleportBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean teleport(Object server, Object player, Object handle, Object destination) {
        Object from = BridgeReflection.invoke(player, "getLocation");
        Object to = destination;

        Object fromWorld = WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(BridgeReflection.invoke(from, "getWorld"));
        Object toWorld = WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(BridgeReflection.invoke(to, "getWorld"));

        if (fromWorld == toWorld) {
            Object netServerHandler = BridgeReflection.getField(handle, "netServerHandler");
            BridgeReflection.invoke(netServerHandler, "teleport", to);
        } else {
            Object serverHandle = BridgeReflection.invoke(server, "getHandle");
            int dimension = ((Number) BridgeReflection.getField(toWorld, "dimension")).intValue();
            try {
                BridgeReflection.invoke(serverHandle, "moveToWorld", handle, dimension, to);
            } catch (Exception ignored) {
                BridgeReflection.invoke(serverHandle, "moveToWorld", handle, dimension);
            }
        }

        return true;
    }
}
