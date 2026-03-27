package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Canonical behavior for CraftMapView state orchestration, renderer lifecycle, and render cache management.
 */
public final class CraftMapViewBehaviour {
    private static final CraftMapViewBehaviour INSTANCE = new CraftMapViewBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();

    private CraftMapViewBehaviour() {
    }

    public static CraftMapViewBehaviour getInstance() {
        return INSTANCE;
    }

    public short getId(String mapId) {
        if (mapId.startsWith("map_")) {
            try {
                return Short.parseShort(mapId.substring("map_".length()));
            } catch (NumberFormatException ex) {
                throw new IllegalStateException("Map has non-numeric ID");
            }
        }

        throw new IllegalStateException("Map has invalid ID");
    }

    public boolean isVirtual(List<MapRenderer> renderers) {
        return renderers.size() > 0 && !(renderers.get(0) instanceof com.legacyminecraft.compat.bukkit.craftbukkit.map.CraftMapRenderer);
    }

    public Scale getScale(byte scaleValue) {
        return Scale.valueOf(scaleValue);
    }

    public void setScale(WorldMap worldMap, Scale scale) {
        worldMap.e = scale.getValue();
    }

    public World getWorld(byte dimension, List<World> worlds) {
        for (World world : worlds) {
            if (WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveDimension(world) == dimension) {
                return world;
            }
        }

        return null;
    }

    public void setWorld(WorldMap worldMap, World world) {
        worldMap.map = WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveDimension(world);
    }

    public int getCenterX(WorldMap worldMap) {
        return worldMap.b;
    }

    public int getCenterZ(WorldMap worldMap) {
        return worldMap.c;
    }

    public void setCenterX(WorldMap worldMap, int x) {
        worldMap.b = x;
    }

    public void setCenterZ(WorldMap worldMap, int z) {
        worldMap.c = z;
    }

    public List<MapRenderer> copyRenderers(List<MapRenderer> renderers) {
        return new ArrayList<MapRenderer>(renderers);
    }

    public void addRenderer(
            CraftMapView mapView,
            List<MapRenderer> renderers,
            Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases,
            MapRenderer renderer
    ) {
        if (renderers.contains(renderer)) {
            return;
        }

        renderers.add(renderer);
        canvases.put(renderer, new HashMap<CraftPlayer, CraftMapCanvas>());
        renderer.initialize(mapView);
    }

    public void addDefaultRenderer(
            CraftMapView mapView,
            WorldMap worldMap,
            List<MapRenderer> renderers,
            Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases
    ) {
        addRenderer(mapView, renderers, canvases, new com.legacyminecraft.compat.bukkit.craftbukkit.map.CraftMapRenderer(mapView, worldMap));
    }

    public boolean removeRenderer(
            List<MapRenderer> renderers,
            Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases,
            MapRenderer renderer
    ) {
        if (!renderers.contains(renderer)) {
            return false;
        }

        renderers.remove(renderer);
        Map<CraftPlayer, CraftMapCanvas> rendererCanvases = canvases.get(renderer);
        for (Map.Entry<CraftPlayer, CraftMapCanvas> entry : rendererCanvases.entrySet()) {
            clearCanvas(entry.getValue());
        }
        canvases.remove(renderer);
        return true;
    }

    public RenderData render(
            CraftMapView mapView,
            Map<CraftPlayer, RenderData> renderCache,
            List<MapRenderer> renderers,
            Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases,
            CraftPlayer player
    ) {
        boolean contextual = isContextual(renderers);
        RenderData render = renderCache.get(contextual ? player : null);
        if (render == null) {
            render = new RenderData();
            renderCache.put(contextual ? player : null, render);
        }

        if (contextual && renderCache.containsKey(null)) {
            renderCache.remove(null);
        }

        Arrays.fill(render.buffer, (byte) 0);
        render.cursors.clear();

        for (MapRenderer renderer : renderers) {
            Map<CraftPlayer, CraftMapCanvas> rendererCanvases = canvases.get(renderer);
            CraftMapCanvas canvas = rendererCanvases.get(renderer.isContextual() ? player : null);
            if (canvas == null) {
                canvas = createCanvas(mapView);
                rendererCanvases.put(renderer.isContextual() ? player : null, canvas);
            }

            setBase(canvas, render.buffer);
            renderer.render(mapView, canvas, player);

            byte[] buffer = getBuffer(canvas);
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] >= 0) {
                    render.buffer[i] = buffer[i];
                }
            }

            for (int i = 0; i < canvas.getCursors().size(); ++i) {
                MapCursor cursor = canvas.getCursors().getCursor(i);
                render.cursors.add(cursor);
            }
        }

        return render;
    }

    private boolean isContextual(List<MapRenderer> renderers) {
        for (MapRenderer renderer : renderers) {
            if (renderer.isContextual()) {
                return true;
            }
        }
        return false;
    }

    private CraftMapCanvas createCanvas(CraftMapView mapView) {
        return new CraftMapCanvas(mapView);
    }

    private void setBase(CraftMapCanvas canvas, byte[] base) {
        canvas.setBase(base);
    }

    private byte[] getBuffer(CraftMapCanvas canvas) {
        return canvas.getBuffer();
    }

    private void clearCanvas(CraftMapCanvas canvas) {
        for (int x = 0; x < 128; ++x) {
            for (int y = 0; y < 128; ++y) {
                canvas.setPixel(x, y, (byte) -1);
            }
        }
    }
}
