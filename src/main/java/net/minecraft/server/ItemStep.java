package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemDataVariantBehaviour;

public class ItemStep extends ItemBlock {
    private static final ItemDataVariantBehaviour ITEM_DATA_VARIANT_BEHAVIOUR = ItemDataVariantBehaviour.getInstance();

    public ItemStep(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return ITEM_DATA_VARIANT_BEHAVIOUR.identity(i);
    }
}
