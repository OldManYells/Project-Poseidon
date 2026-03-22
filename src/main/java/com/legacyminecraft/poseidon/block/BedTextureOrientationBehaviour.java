package com.legacyminecraft.poseidon.block;

public final class BedTextureOrientationBehaviour {
    private static final BedTextureOrientationBehaviour INSTANCE = new BedTextureOrientationBehaviour();

    private BedTextureOrientationBehaviour() {
    }

    public static BedTextureOrientationBehaviour getInstance() {
        return INSTANCE;
    }

    public int[] getHeadAndFootFaces() {
        return new int[] {3, 4, 2, 5};
    }

    public int[] getSideFaces() {
        return new int[] {2, 3, 0, 1};
    }

    public int[][] getRotationTextureMap() {
        return new int[][] {
                {1, 0, 3, 2, 5, 4},
                {1, 0, 5, 4, 2, 3},
                {1, 0, 2, 3, 4, 5},
                {1, 0, 4, 5, 3, 2}
        };
    }
}
