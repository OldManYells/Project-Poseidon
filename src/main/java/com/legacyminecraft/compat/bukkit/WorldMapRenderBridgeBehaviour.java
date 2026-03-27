package com.legacyminecraft.compat.bukkit;


import java.util.List;

/**
 * Canonical Bukkit bridge behaviour for CraftBukkit map rendering hooks.
 */
public final class WorldMapRenderBridgeBehaviour {
    private static final WorldMapRenderBridgeBehaviour INSTANCE = new WorldMapRenderBridgeBehaviour();
    private static final PlayerWrapperProjectionBridgeBehaviour PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            PlayerWrapperProjectionBridgeBehaviour.getInstance();

    private WorldMapRenderBridgeBehaviour() {
    }

    public static WorldMapRenderBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public RenderSnapshot render(Object worldMap, Object trackee) {
        Object bukkitEntity = BridgeReflection.invoke(trackee, "getBukkitEntity");
        Object craftPlayer = PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.requireCraftPlayer(bukkitEntity);
        Object mapView = BridgeReflection.getField(worldMap, "mapView");
        Object renderData = BridgeReflection.invoke(mapView, "render", craftPlayer);
        byte[] buffer = BridgeReflection.cast(BridgeReflection.getField(renderData, "buffer"));
        List<MapCursor> cursors = BridgeReflection.cast(BridgeReflection.getField(renderData, "cursors"));
        return new RenderSnapshot(buffer, cursors);
    }

    public static final class RenderSnapshot {
        private final byte[] buffer;
        private final List<MapCursor> cursors;

        RenderSnapshot(byte[] buffer, List<MapCursor> cursors) {
            this.buffer = buffer;
            this.cursors = cursors;
        }

        public byte[] getBuffer() {
            return buffer;
        }

        public List<MapCursor> getCursors() {
            return cursors;
        }
    }
}
