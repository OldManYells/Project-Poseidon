package net.minecraft.server;

import com.legacyminecraft.poseidon.item.BucketItemBehaviour;

public class ItemBucket extends Item {
    private static final BucketItemBehaviour BUCKET_ITEM_BEHAVIOUR = BucketItemBehaviour.getInstance();

    private int a;

    public ItemBucket(int i, int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return BUCKET_ITEM_BEHAVIOUR.use(itemstack, world, entityhuman, this.a);
    }
}
