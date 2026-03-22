package com.legacyminecraft.poseidon.block;

public final class PistonTextureOrientationBehaviour {
    private static final PistonTextureOrientationBehaviour INSTANCE = new PistonTextureOrientationBehaviour();

    private PistonTextureOrientationBehaviour() {
    }

    public static PistonTextureOrientationBehaviour getInstance() {
        return INSTANCE;
    }

    public int[] getFaceRotationMap() {
        return new int[] {1, 0, 3, 2, 5, 4};
    }

    public int[] getXOffsets() {
        return new int[] {0, 0, 0, 0, -1, 1};
    }

    public int[] getYOffsets() {
        return new int[] {-1, 1, 0, 0, 0, 0};
    }

    public int[] getZOffsets() {
        return new int[] {0, 0, -1, 1, 0, 0};
    }
}
