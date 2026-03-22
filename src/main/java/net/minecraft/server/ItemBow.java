package net.minecraft.server;

import com.legacyminecraft.poseidon.item.BowUseBehaviour;

public class ItemBow extends Item {
    private static final BowUseBehaviour BOW_USE_BEHAVIOUR = BowUseBehaviour.getInstance();

    public ItemBow(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return BOW_USE_BEHAVIOUR.use(itemstack, world, entityhuman, b);
    }
}
