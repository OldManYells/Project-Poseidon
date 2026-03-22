package net.minecraft.server;

import com.legacyminecraft.poseidon.item.FoodItemConsumptionBehaviour;

public class ItemFood extends Item {
    private static final FoodItemConsumptionBehaviour FOOD_ITEM_CONSUMPTION_BEHAVIOUR = FoodItemConsumptionBehaviour.getInstance();

    private int a;
    private boolean bk;

    public ItemFood(int i, int j, boolean flag) {
        super(i);
        this.a = j;
        this.bk = flag;
        this.maxStackSize = 1;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return FOOD_ITEM_CONSUMPTION_BEHAVIOUR.consume(itemstack, world, entityhuman, this.a);
    }

    public int k() {
        return this.a;
    }

    public boolean l() {
        return this.bk;
    }
}
