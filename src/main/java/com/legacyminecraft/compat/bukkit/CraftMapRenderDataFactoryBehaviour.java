package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;

/**
 * Canonical behavior for CraftBukkit map render-data allocation.
 */
public final class CraftMapRenderDataFactoryBehaviour {
    private static final CraftMapRenderDataFactoryBehaviour INSTANCE =
            new CraftMapRenderDataFactoryBehaviour();
    private static final int MAP_WIDTH = 128;
    private static final int MAP_HEIGHT = 128;

    private CraftMapRenderDataFactoryBehaviour() {
    }

    public static CraftMapRenderDataFactoryBehaviour getInstance() {
        return INSTANCE;
    }

    public byte[] createBuffer() {
        return new byte[MAP_WIDTH * MAP_HEIGHT];
    }

    public ArrayList createCursors() {
        return new ArrayList();
    }
}
