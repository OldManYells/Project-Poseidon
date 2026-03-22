package net.minecraft.server;

import com.legacyminecraft.poseidon.item.FlintAndSteelItemPlacementBehaviour;

public class ItemFlintAndSteel extends Item {
    private static final FlintAndSteelItemPlacementBehaviour FLINT_AND_STEEL_ITEM_PLACEMENT_BEHAVIOUR = FlintAndSteelItemPlacementBehaviour.getInstance();

    public ItemFlintAndSteel(int i) {
        super(i);
        this.maxStackSize = 1;
        this.d(64);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return FLINT_AND_STEEL_ITEM_PLACEMENT_BEHAVIOUR.placeFire(itemstack, entityhuman, world, i, j, k, l, b);
    }
}
