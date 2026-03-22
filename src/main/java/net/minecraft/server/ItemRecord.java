package net.minecraft.server;

import com.legacyminecraft.poseidon.item.RecordItemPlacementBehaviour;

public class ItemRecord extends Item {
    private static final RecordItemPlacementBehaviour RECORD_ITEM_PLACEMENT_BEHAVIOUR = RecordItemPlacementBehaviour.getInstance();

    public final String a;

    protected ItemRecord(int i, String s) {
        super(i);
        this.a = s;
        this.maxStackSize = 1;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return RECORD_ITEM_PLACEMENT_BEHAVIOUR.placeRecord(itemstack, entityhuman, world, i, j, k, l, this.id);
    }
}
