package net.minecraft.server;

import com.legacyminecraft.poseidon.item.PaintingItemPlacementBehaviour;

public class ItemPainting extends Item {
    private static final PaintingItemPlacementBehaviour PAINTING_ITEM_PLACEMENT_BEHAVIOUR = PaintingItemPlacementBehaviour.getInstance();

    public ItemPainting(int i) {
        super(i);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return PAINTING_ITEM_PLACEMENT_BEHAVIOUR.place(itemstack, entityhuman, world, i, j, k, l);
    }
}
