package com.legacyminecraft.poseidon.block;

public final class MaterialPropertyBehaviour {
    private static final MaterialPropertyBehaviour INSTANCE = new MaterialPropertyBehaviour();

    private MaterialPropertyBehaviour() {
    }

    public static MaterialPropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isLiquidByDefault() {
        return false;
    }

    public boolean isBuildableByDefault() {
        return true;
    }

    public boolean blocksLightByDefault() {
        return true;
    }

    public boolean isSolidByDefault() {
        return true;
    }

    public boolean isBurnable(boolean canBurn) {
        return canBurn;
    }

    public boolean isReplaceable(boolean replaceable) {
        return replaceable;
    }

    public boolean blocksMovement(boolean noPush, boolean solid) {
        return noPush ? false : solid;
    }

    public boolean isOpaqueToLight(boolean transparentForLight) {
        return transparentForLight;
    }

    public int getPushReaction(int pushReaction) {
        return pushReaction;
    }
}
