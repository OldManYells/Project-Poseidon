package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical drop policy for legacy gravel wrappers.
 */
public final class GravelDropBehaviour {
    private static final GravelDropBehaviour INSTANCE = new GravelDropBehaviour();

    private GravelDropBehaviour() {
    }

    public static GravelDropBehaviour getInstance() {
        return INSTANCE;
    }

    public int flintChanceDivisor() {
        return 10;
    }

    public int resolveDropItemId(Random random, int blockId, int flintItemId, int chanceDivisor) {
        return random.nextInt(chanceDivisor) == 0 ? flintItemId : blockId;
    }
}
