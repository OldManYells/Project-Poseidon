package net.minecraft.server;

import com.legacyminecraft.poseidon.item.BoatItemPlacementBehaviour;

public class ItemBoat extends Item {
    private static final BoatItemPlacementBehaviour BOAT_ITEM_PLACEMENT_BEHAVIOUR = BoatItemPlacementBehaviour.getInstance();

    public ItemBoat(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return BOAT_ITEM_PLACEMENT_BEHAVIOUR.use(itemstack, world, entityhuman);
    }
}
