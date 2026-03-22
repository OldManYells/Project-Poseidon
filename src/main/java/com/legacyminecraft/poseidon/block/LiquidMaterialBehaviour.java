package com.legacyminecraft.poseidon.block;

public final class LiquidMaterialBehaviour {
    private static final LiquidMaterialBehaviour INSTANCE = new LiquidMaterialBehaviour();

    private LiquidMaterialBehaviour() {
    }

    public static LiquidMaterialBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isLiquid() {
        return true;
    }
}
