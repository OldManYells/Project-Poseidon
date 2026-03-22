package net.minecraft.server;

import com.legacyminecraft.poseidon.item.SeedsItemPlacementBehaviour;

public class ItemSeeds extends Item {
    private static final SeedsItemPlacementBehaviour SEEDS_ITEM_PLACEMENT_BEHAVIOUR = SeedsItemPlacementBehaviour.getInstance();

    private int id;

    public ItemSeeds(int i, int j) {
        super(i);
        this.id = j;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return SEEDS_ITEM_PLACEMENT_BEHAVIOUR.place(itemstack, entityhuman, world, i, j, k, l, this.id);
    }
}
