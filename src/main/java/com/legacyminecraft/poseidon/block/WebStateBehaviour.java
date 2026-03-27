package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.entity.Entity;

/**
 * Canonical entity interaction and drop policy for legacy web wrappers.
 */
public final class WebStateBehaviour {
    private static final WebStateBehaviour INSTANCE = new WebStateBehaviour();

    private WebStateBehaviour() {
    }

    public static WebStateBehaviour getInstance() {
        return INSTANCE;
    }

    public void applyEntanglement(Entity entity) {
        entity.bf = true;
    }

    public int resolveDropItemId(int stringItemId) {
        return stringItemId;
    }
}
