package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for dirty-block tracking within a player chunk instance.
 */
public final class PlayerChunkDirtyBlockBehaviour {
    private static final PlayerChunkDirtyBlockBehaviour INSTANCE = new PlayerChunkDirtyBlockBehaviour();

    private PlayerChunkDirtyBlockBehaviour() {
    }

    public static PlayerChunkDirtyBlockBehaviour getInstance() {
        return INSTANCE;
    }

    public short encodeDirtyBlock(int localX, int localY, int localZ) {
        return (short) (localX << 12 | localZ << 8 | localY);
    }

    public int decodeLocalX(short encodedDirtyBlock) {
        return encodedDirtyBlock >> 12 & 15;
    }

    public int decodeY(short encodedDirtyBlock) {
        return encodedDirtyBlock & 255;
    }

    public int decodeLocalZ(short encodedDirtyBlock) {
        return encodedDirtyBlock >> 8 & 15;
    }

    public boolean containsDirtyBlock(short[] dirtyBlocks, int dirtyCount, short encodedDirtyBlock) {
        for (int index = 0; index < dirtyCount; ++index) {
            if (dirtyBlocks[index] == encodedDirtyBlock) {
                return true;
            }
        }

        return false;
    }

    public int appendDirtyBlock(short[] dirtyBlocks, int dirtyCount, short encodedDirtyBlock) {
        dirtyBlocks[dirtyCount] = encodedDirtyBlock;
        return dirtyCount + 1;
    }

    public int min(int current, int candidate) {
        return current > candidate ? candidate : current;
    }

    public int max(int current, int candidate) {
        return current < candidate ? candidate : current;
    }
}
