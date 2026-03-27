package com.legacyminecraft.poseidon.block;


/**
 * Canonical texture, placement, and orientation policy for legacy pumpkin wrappers.
 */
public final class PumpkinStateBehaviour {
    private static final PumpkinStateBehaviour INSTANCE = new PumpkinStateBehaviour();

    private PumpkinStateBehaviour() {
    }

    public static PumpkinStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySideAndData(int side, int data, int textureId, boolean litPumpkin) {
        if (side == 1 || side == 0) {
            return textureId;
        }

        int faceTexture = textureId + 1 + 16;
        if (litPumpkin) {
            ++faceTexture;
        }

        return data == 2 && side == 2
                ? faceTexture
                : (data == 3 && side == 5
                ? faceTexture
                : (data == 0 && side == 3
                ? faceTexture
                : (data == 1 && side == 4 ? faceTexture : textureId + 16)));
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1
                ? textureId
                : (side == 0 ? textureId : (side == 3 ? textureId + 1 + 16 : textureId + 16));
    }

    public boolean canPlace(boolean emptyOrReplaceable, boolean hasSupportBelow) {
        return emptyOrReplaceable && hasSupportBelow;
    }

    public int resolvePlacementDataFromYaw(float yaw) {
        return MathHelper.floor((double) (yaw * 4.0F / 360.0F) + 2.5D) & 3;
    }
}
