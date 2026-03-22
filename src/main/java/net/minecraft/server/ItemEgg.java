package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ThrowableItemBehaviour;

public class ItemEgg extends Item {
    private static final ThrowableItemBehaviour THROWABLE_ITEM_BEHAVIOUR = ThrowableItemBehaviour.getInstance();

    public ItemEgg(int i) {
        super(i);
        this.maxStackSize = 16;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return THROWABLE_ITEM_BEHAVIOUR.throwEgg(itemstack, world, entityhuman, b);
    }
}
