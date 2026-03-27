package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge behaviour for CraftPlayer world/map context projection.
 */
public final class PlayerContextBridgeBehaviour {
    private static final PlayerContextBridgeBehaviour INSTANCE = new PlayerContextBridgeBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();
    private static final MapViewRenderProjectionBehaviour MAP_VIEW_RENDER_PROJECTION_BEHAVIOUR =
            MapViewRenderProjectionBehaviour.getInstance();

    private PlayerContextBridgeBehaviour() {
    }

    public static PlayerContextBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveWorldHandle(Object world) {
        return WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world);
    }

    public <T> T renderMapData(Object map, Object player) {
        return MAP_VIEW_RENDER_PROJECTION_BEHAVIOUR.renderMapData(map, player);
    }
}
