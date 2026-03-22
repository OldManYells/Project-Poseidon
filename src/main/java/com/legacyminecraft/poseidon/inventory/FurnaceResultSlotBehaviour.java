package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.AchievementList;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public final class FurnaceResultSlotBehaviour {
    private static final FurnaceResultSlotBehaviour INSTANCE = new FurnaceResultSlotBehaviour();

    private FurnaceResultSlotBehaviour() {
    }

    public static FurnaceResultSlotBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public void onSmelted(ItemStack itemstack, EntityHuman human) {
        itemstack.b(human.world, human);
        if (itemstack.id == Item.IRON_INGOT.id) {
            human.a(AchievementList.k, 1);
        }

        if (itemstack.id == Item.COOKED_FISH.id) {
            human.a(AchievementList.p, 1);
        }
    }
}
