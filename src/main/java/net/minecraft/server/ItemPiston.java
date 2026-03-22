package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemDataVariantBehaviour;

public class ItemPiston extends ItemBlock {
    private static final ItemDataVariantBehaviour ITEM_DATA_VARIANT_BEHAVIOUR = ItemDataVariantBehaviour.getInstance();

    public ItemPiston(int i) {
        super(i);
    }

    public int filterData(int i) {
        return ITEM_DATA_VARIANT_BEHAVIOUR.pistonPlacementData();
    }
}
