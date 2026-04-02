package com.legacyminecraft.poseidon.world.inventory;

import com.legacyminecraft.poseidon.api.IInventory;
import com.legacyminecraft.poseidon.world.block.Block;
import com.legacyminecraft.poseidon.world.item.ItemArmor;
import com.legacyminecraft.poseidon.world.item.ItemStack;

class SlotArmor extends Slot {

    final int d;

    final ContainerPlayer e;

    SlotArmor(ContainerPlayer containerplayer, IInventory iinventory, int i, int j, int k, int l) {
        super(iinventory, i, j, k);
        this.e = containerplayer;
        this.d = l;
    }

    public int d() {
        return 1;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemArmor ? ((ItemArmor) itemstack.getItem()).bk == this.d : (itemstack.getItem().id == Block.PUMPKIN.id ? this.d == 0 : false);
    }
}
