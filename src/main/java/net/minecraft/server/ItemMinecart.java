package net.minecraft.server;

import com.legacyminecraft.poseidon.item.MinecartItemPlacementBehaviour;

public class ItemMinecart extends Item {
    private static final MinecartItemPlacementBehaviour MINECART_ITEM_PLACEMENT_BEHAVIOUR = MinecartItemPlacementBehaviour.getInstance();

    public int a;

    public ItemMinecart(int i, int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return MINECART_ITEM_PLACEMENT_BEHAVIOUR.place(itemstack, entityhuman, world, i, j, k, l, this.a);
    }
}
