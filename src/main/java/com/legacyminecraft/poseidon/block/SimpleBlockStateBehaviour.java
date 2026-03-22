package com.legacyminecraft.poseidon.block;

/**
 * Canonical simple texture/drop/opacity policy shared by legacy lightweight block wrappers.
 */
public final class SimpleBlockStateBehaviour {
    private static final SimpleBlockStateBehaviour INSTANCE = new SimpleBlockStateBehaviour();

    private SimpleBlockStateBehaviour() {
    }

    public static SimpleBlockStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOpaqueCubeFalse() {
        return false;
    }

    public int resolveBookshelfTextureBySide(int side, int textureId) {
        return side <= 1 ? 4 : textureId;
    }

    public int resolveNoDropCount() {
        return 0;
    }

    public int resolveFixedDropCount(int count) {
        return count;
    }

    public int resolveFixedDropItemId(int itemId) {
        return itemId;
    }

    public int resolveStoneDropItemId(int cobblestoneBlockId) {
        return cobblestoneBlockId;
    }

    public int resolveTextureIdentity(int textureId) {
        return textureId;
    }
}
