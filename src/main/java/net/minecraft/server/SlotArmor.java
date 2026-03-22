package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ArmorSlotAcceptanceBehaviour;

class SlotArmor extends Slot {
    private static final ArmorSlotAcceptanceBehaviour ARMOR_SLOT_ACCEPTANCE_BEHAVIOUR = ArmorSlotAcceptanceBehaviour.getInstance();

    final int d;

    final ContainerPlayer e;

    SlotArmor(ContainerPlayer containerplayer, IInventory iinventory, int i, int j, int k, int l) {
        super(iinventory, i, j, k);
        this.e = containerplayer;
        this.d = l;
    }

    public int d() {
        return ARMOR_SLOT_ACCEPTANCE_BEHAVIOUR.maxStackSize();
    }

    public boolean isAllowed(ItemStack itemstack) {
        return ARMOR_SLOT_ACCEPTANCE_BEHAVIOUR.isAllowed(itemstack, this.d);
    }
}
