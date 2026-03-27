package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local food item scaffold.
 */
public class ItemFood extends Item {
    private final boolean wolfFood;
    private final int healAmount;

    public ItemFood(int id, boolean wolfFood, int healAmount) {
        super(id);
        this.wolfFood = wolfFood;
        this.healAmount = healAmount;
    }

    public boolean l() {
        return wolfFood;
    }

    public int k() {
        return healAmount;
    }
}
