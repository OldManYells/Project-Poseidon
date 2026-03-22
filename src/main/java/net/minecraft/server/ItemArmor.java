package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ArmorItemStatsBehaviour;

public class ItemArmor extends Item {
    private static final ArmorItemStatsBehaviour ARMOR_ITEM_STATS_BEHAVIOUR = ArmorItemStatsBehaviour.getInstance();

    private static final int[] bn = new int[] { 3, 8, 6, 3};
    private static final int[] bo = new int[] { 11, 16, 15, 13};
    public final int a;
    public final int bk;
    public final int bl;
    public final int bm;

    public ItemArmor(int i, int j, int k, int l) {
        super(i);
        this.a = j;
        this.bk = l;
        this.bm = k;
        this.bl = ARMOR_ITEM_STATS_BEHAVIOUR.resolveArmorPoints(l);
        this.d(ARMOR_ITEM_STATS_BEHAVIOUR.resolveDurability(l, j));
        this.maxStackSize = 1;
    }
}
