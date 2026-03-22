package net.minecraft.server;

import com.legacyminecraft.poseidon.item.FishingRodUseBehaviour;

public class ItemFishingRod extends Item {
    private static final FishingRodUseBehaviour FISHING_ROD_USE_BEHAVIOUR = FishingRodUseBehaviour.getInstance();

    public ItemFishingRod(int i) {
        super(i);
        this.d(64);
        this.c(1);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return FISHING_ROD_USE_BEHAVIOUR.use(itemstack, world, entityhuman, b);
    }
}
