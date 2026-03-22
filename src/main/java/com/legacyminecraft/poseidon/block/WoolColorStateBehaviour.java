package com.legacyminecraft.poseidon.block;

/**
 * Canonical texture/color inversion policy for legacy wool wrappers.
 */
public final class WoolColorStateBehaviour {
    private static final WoolColorStateBehaviour INSTANCE = new WoolColorStateBehaviour();

    private WoolColorStateBehaviour() {
    }

    public static WoolColorStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureByData(int textureId, int data) {
        if (data == 0) {
            return textureId;
        }
        int inverted = ~(data & 15);
        return 113 + ((inverted & 8) >> 3) + (inverted & 7) * 16;
    }

    public int resolveDroppedData(int data) {
        return data;
    }

    public int invertColorData(int data) {
        return ~data & 15;
    }
}
