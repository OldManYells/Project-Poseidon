package com.legacyminecraft.poseidon.item;

public final class ToolMaterialBehaviour {
    private static final ToolMaterialBehaviour INSTANCE = new ToolMaterialBehaviour();

    private ToolMaterialBehaviour() {
    }

    public static ToolMaterialBehaviour getInstance() {
        return INSTANCE;
    }

    public int getDurability(int durability) {
        return durability;
    }

    public float getMiningSpeed(float miningSpeed) {
        return miningSpeed;
    }

    public int getAttackDamage(int attackDamage) {
        return attackDamage;
    }

    public int getHarvestLevel(int harvestLevel) {
        return harvestLevel;
    }
}
