package com.legacyminecraft.poseidon.block;

public final class NonSolidMaterialBehaviour {
    private static final NonSolidMaterialBehaviour INSTANCE = new NonSolidMaterialBehaviour();

    private NonSolidMaterialBehaviour() {
    }

    public static NonSolidMaterialBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isBuildable() {
        return false;
    }

    public boolean blocksLight() {
        return false;
    }

    public boolean isSolid() {
        return false;
    }
}
