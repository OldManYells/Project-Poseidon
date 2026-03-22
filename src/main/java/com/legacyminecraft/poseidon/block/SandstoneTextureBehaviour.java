package com.legacyminecraft.poseidon.block;

/**
 * Canonical side texture policy for legacy sandstone wrappers.
 */
public final class SandstoneTextureBehaviour {
    private static final SandstoneTextureBehaviour INSTANCE = new SandstoneTextureBehaviour();

    private SandstoneTextureBehaviour() {
    }

    public static SandstoneTextureBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1 ? textureId - 16 : (side == 0 ? textureId + 16 : textureId);
    }
}
