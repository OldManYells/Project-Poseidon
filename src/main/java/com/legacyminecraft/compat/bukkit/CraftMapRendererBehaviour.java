package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftBukkit default map rendering.
 */
public final class CraftMapRendererBehaviour {
    private static final CraftMapRendererBehaviour INSTANCE = new CraftMapRendererBehaviour();
    private static final int MAP_WIDTH = 128;
    private static final int MAP_HEIGHT = 128;

    private CraftMapRendererBehaviour() {
    }

    public static CraftMapRendererBehaviour getInstance() {
        return INSTANCE;
    }

    public void render(WorldMap worldMap, MapCanvas canvas) {
        renderPixels(worldMap, canvas);
        renderCursors(worldMap, canvas.getCursors());
    }

    private void renderPixels(WorldMap worldMap, MapCanvas canvas) {
        for (int x = 0; x < MAP_WIDTH; ++x) {
            for (int y = 0; y < MAP_HEIGHT; ++y) {
                canvas.setPixel(x, y, worldMap.f[y * MAP_WIDTH + x]);
            }
        }
    }

    private void renderCursors(WorldMap worldMap, MapCursorCollection cursors) {
        while (cursors.size() > 0) {
            cursors.removeCursor(cursors.getCursor(0));
        }

        for (int index = 0; index < worldMap.i.size(); ++index) {
            WorldMapOrienter orienter = (WorldMapOrienter) worldMap.i.get(index);
            cursors.addCursor(orienter.b, orienter.c, (byte) (orienter.d & 15), (byte) orienter.a);
        }
    }
}
