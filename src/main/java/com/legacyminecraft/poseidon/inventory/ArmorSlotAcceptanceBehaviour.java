package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Block;
import net.minecraft.server.ItemArmor;
import net.minecraft.server.ItemStack;

public final class ArmorSlotAcceptanceBehaviour {
    private static final ArmorSlotAcceptanceBehaviour INSTANCE = new ArmorSlotAcceptanceBehaviour();

    private ArmorSlotAcceptanceBehaviour() {
    }

    public static ArmorSlotAcceptanceBehaviour getInstance() {
        return INSTANCE;
    }

    public int maxStackSize() {
        return 1;
    }

    public boolean isAllowed(ItemStack itemstack, int armorType) {
        return itemstack.getItem() instanceof ItemArmor
                ? ((ItemArmor) itemstack.getItem()).bk == armorType
                : (itemstack.getItem().id == Block.PUMPKIN.id && armorType == 0);
    }
}
