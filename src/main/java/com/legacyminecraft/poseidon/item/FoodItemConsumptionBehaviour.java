package com.legacyminecraft.poseidon.item;


public final class FoodItemConsumptionBehaviour {
    private static final FoodItemConsumptionBehaviour INSTANCE = new FoodItemConsumptionBehaviour();

    private FoodItemConsumptionBehaviour() {
    }

    public static FoodItemConsumptionBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack consume(ItemStack itemstack, World world, EntityHuman entityhuman, int healAmount) {
        --itemstack.count;
        entityhuman.b(healAmount);
        return itemstack;
    }

    public ItemStack consumeSoup(ItemStack itemstack, World world, EntityHuman entityhuman, int healAmount) {
        consume(itemstack, world, entityhuman, healAmount);
        return new ItemStack(Item.BOWL);
    }
}
