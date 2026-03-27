package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for rendering map data through CraftMapView projection.
 */
public final class MapViewRenderProjectionBehaviour {
    private static final MapViewRenderProjectionBehaviour INSTANCE = new MapViewRenderProjectionBehaviour();
    private static final PlayerWrapperProjectionBridgeBehaviour PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            PlayerWrapperProjectionBridgeBehaviour.getInstance();
    private static final MapViewWrapperProjectionBridgeBehaviour MAP_VIEW_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            MapViewWrapperProjectionBridgeBehaviour.getInstance();

    private MapViewRenderProjectionBehaviour() {
    }

    public static MapViewRenderProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T renderMapData(Object map, Object player) {
        Object craftMapView = MAP_VIEW_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftMapView(BridgeReflection.cast(map));
        Object craftPlayer = PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.requireCraftPlayer(BridgeReflection.cast(player));
        return BridgeReflection.cast(BridgeReflection.invoke(craftMapView, "render", craftPlayer));
    }
}
