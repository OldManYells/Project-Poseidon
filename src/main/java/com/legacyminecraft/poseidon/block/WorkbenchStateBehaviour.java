package com.legacyminecraft.poseidon.block;

/**
 * Canonical texture and interaction policy for legacy workbench wrappers.
 */
public final class WorkbenchStateBehaviour {
    private static final WorkbenchStateBehaviour INSTANCE = new WorkbenchStateBehaviour();

    private WorkbenchStateBehaviour() {
    }

    public static WorkbenchStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySide(int side, int textureId, int woodTextureId) {
        return side == 1 ? textureId - 16 : (side == 0 ? woodTextureId : (side != 2 && side != 4 ? textureId : textureId + 1));
    }

    public boolean shouldIgnoreClientInteraction(boolean worldIsStatic) {
        return worldIsStatic;
    }
}
