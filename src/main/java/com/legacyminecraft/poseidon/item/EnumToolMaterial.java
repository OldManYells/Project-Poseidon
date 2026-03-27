package com.legacyminecraft.poseidon.item;

/**
 * Item-local tool material scaffold.
 */
public enum EnumToolMaterial {
    WOOD(0, 59, 2.0F, 0),
    STONE(1, 131, 4.0F, 1),
    IRON(2, 250, 6.0F, 2),
    DIAMOND(3, 1561, 8.0F, 3),
    GOLD(0, 32, 12.0F, 0);

    private final int harvestLevel;
    private final int durability;
    private final float miningSpeed;
    private final int attackBonus;

    EnumToolMaterial(int harvestLevel, int durability, float miningSpeed, int attackBonus) {
        this.harvestLevel = harvestLevel;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackBonus = attackBonus;
    }

    public int a() {
        return durability;
    }

    public float b() {
        return miningSpeed;
    }

    public int c() {
        return attackBonus;
    }

    public int d() {
        return harvestLevel;
    }
}
