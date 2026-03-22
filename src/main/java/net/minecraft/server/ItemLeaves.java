package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemDataVariantBehaviour;

public class ItemLeaves extends ItemBlock {
    private static final ItemDataVariantBehaviour ITEM_DATA_VARIANT_BEHAVIOUR = ItemDataVariantBehaviour.getInstance();

    public ItemLeaves(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return ITEM_DATA_VARIANT_BEHAVIOUR.leavesPlacementData(i);
    }
}
