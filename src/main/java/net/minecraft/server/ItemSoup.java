package net.minecraft.server;

import com.legacyminecraft.poseidon.item.FoodItemConsumptionBehaviour;

public class ItemSoup extends ItemFood {
    private static final FoodItemConsumptionBehaviour FOOD_ITEM_CONSUMPTION_BEHAVIOUR = FoodItemConsumptionBehaviour.getInstance();

    public ItemSoup(int i, int j) {
        super(i, j, false);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return FOOD_ITEM_CONSUMPTION_BEHAVIOUR.consumeSoup(itemstack, world, entityhuman, this.k());
    }
}
