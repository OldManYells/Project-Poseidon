package com.legacyminecraft.poseidon.world.inventory;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.item.*;
import com.legacyminecraft.poseidon.world.block.*;
import com.legacyminecraft.poseidon.world.item.*;

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
