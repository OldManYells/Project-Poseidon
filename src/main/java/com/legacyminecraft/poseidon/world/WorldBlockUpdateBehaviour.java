package com.legacyminecraft.poseidon.world;

import net.minecraft.server.World;

/**
 * Canonical behaviour for world block-update orchestration helpers.
 */
public final class WorldBlockUpdateBehaviour {
    private static final WorldBlockUpdateBehaviour INSTANCE = new WorldBlockUpdateBehaviour();

    private WorldBlockUpdateBehaviour() {
    }

    public static WorldBlockUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public void notifyAndApplyPhysics(World world, int x, int y, int z, int sourceTypeId) {
        world.notify(x, y, z);
        world.applyPhysics(x, y, z, sourceTypeId);
    }

    public VerticalRange normalizeVerticalRange(int y1, int y2) {
        if (y1 <= y2) {
            return new VerticalRange(y1, y2);
        }

        return new VerticalRange(y2, y1);
    }

    public static final class VerticalRange {
        public final int minY;
        public final int maxY;

        public VerticalRange(int minY, int maxY) {
            this.minY = minY;
            this.maxY = maxY;
        }
    }
}
