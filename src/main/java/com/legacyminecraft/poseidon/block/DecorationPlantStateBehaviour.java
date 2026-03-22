package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical support/texture/drop policy for legacy dead-bush and long-grass wrappers.
 */
public final class DecorationPlantStateBehaviour {
    private static final DecorationPlantStateBehaviour INSTANCE = new DecorationPlantStateBehaviour();

    private DecorationPlantStateBehaviour() {
    }

    public static DecorationPlantStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int noDropItemId() {
        return -1;
    }

    public boolean deadBushCanPlaceOn(int belowTypeId, int sandBlockId) {
        return belowTypeId == sandBlockId;
    }

    public int deadBushTexture(int textureId) {
        return textureId;
    }

    public int longGrassTextureByData(int data, int textureId) {
        return data == 1 ? textureId : (data == 2 ? textureId + 17 : (data == 0 ? textureId + 16 : textureId));
    }

    public int longGrassDropItemId(Random random, int seedsItemId) {
        return random.nextInt(8) == 0 ? seedsItemId : noDropItemId();
    }
}
