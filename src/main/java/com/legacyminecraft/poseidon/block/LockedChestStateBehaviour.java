package com.legacyminecraft.poseidon.block;

/**
 * Canonical texture/placement/expiry policy for legacy locked-chest wrappers.
 */
public final class LockedChestStateBehaviour {
    private static final LockedChestStateBehaviour INSTANCE = new LockedChestStateBehaviour();

    private LockedChestStateBehaviour() {
    }

    public static LockedChestStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1 ? textureId - 1 : (side == 0 ? textureId - 1 : (side == 3 ? textureId + 1 : textureId));
    }

    public boolean canPlaceAtAnyLocation() {
        return true;
    }

    public int expiredBlockTypeId() {
        return 0;
    }
}
