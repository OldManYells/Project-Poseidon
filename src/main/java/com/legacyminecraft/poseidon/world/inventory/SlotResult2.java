package com.legacyminecraft.poseidon.world.inventory;

import com.legacyminecraft.poseidon.api.IInventory;
import com.legacyminecraft.poseidon.statistics.AchievementList;
import com.legacyminecraft.poseidon.world.entity.EntityHuman;
import com.legacyminecraft.poseidon.world.item.Item;
import com.legacyminecraft.poseidon.world.item.ItemStack;

public class SlotResult2 extends Slot {

    private EntityHuman d;

    public SlotResult2(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.d = entityhuman;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public void a(ItemStack itemstack) {
        itemstack.b(this.d.world, this.d);
        if (itemstack.id == Item.IRON_INGOT.id) {
            this.d.a(AchievementList.k, 1);
        }

        if (itemstack.id == Item.COOKED_FISH.id) {
            this.d.a(AchievementList.p, 1);
        }

        super.a(itemstack);
    }
}
