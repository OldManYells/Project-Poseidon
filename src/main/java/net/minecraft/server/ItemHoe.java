package net.minecraft.server;

import com.legacyminecraft.poseidon.item.HoeTillingBehaviour;

public class ItemHoe extends Item {
    private static final HoeTillingBehaviour HOE_TILLING_BEHAVIOUR = HoeTillingBehaviour.getInstance();

    public ItemHoe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return HOE_TILLING_BEHAVIOUR.till(itemstack, entityhuman, world, i, j, k, l);
    }
}
