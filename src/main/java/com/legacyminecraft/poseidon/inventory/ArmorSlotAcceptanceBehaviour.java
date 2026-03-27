package com.legacyminecraft.poseidon.inventory;


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
