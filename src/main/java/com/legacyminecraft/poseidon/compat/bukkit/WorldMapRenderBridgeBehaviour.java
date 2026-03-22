package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.WorldMap;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.map.RenderData;
import org.bukkit.map.MapCursor;

import java.util.List;

/**
 * Canonical Bukkit bridge behaviour for CraftBukkit map rendering hooks.
 */
public final class WorldMapRenderBridgeBehaviour {
    private static final WorldMapRenderBridgeBehaviour INSTANCE = new WorldMapRenderBridgeBehaviour();

    private WorldMapRenderBridgeBehaviour() {
    }

    public static WorldMapRenderBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public RenderSnapshot render(WorldMap worldMap, EntityHuman trackee) {
        RenderData render = worldMap.mapView.render((CraftPlayer) trackee.getBukkitEntity());
        return new RenderSnapshot(render.buffer, render.cursors);
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
