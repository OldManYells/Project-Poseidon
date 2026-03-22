package net.minecraft.server;

import com.legacyminecraft.poseidon.item.DyeItemBehaviour;

public class ItemDye extends Item {
    private static final DyeItemBehaviour DYE_ITEM_BEHAVIOUR = DyeItemBehaviour.getInstance();

    public static final String[] a = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
    public static final int[] bk = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDye(int i) {
        super(i);
        this.a(true);
        this.d(0);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return DYE_ITEM_BEHAVIOUR.applyToBlock(itemstack, entityhuman, world, i, j, k, l, b);
    }

    public void a(ItemStack itemstack, EntityLiving entityliving) {
        DYE_ITEM_BEHAVIOUR.applyToEntity(itemstack, entityliving);
    }
}
