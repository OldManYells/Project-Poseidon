package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.AchievementList;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public final class CraftingResultSlotBehaviour {
    private static final CraftingResultSlotBehaviour INSTANCE = new CraftingResultSlotBehaviour();

    private CraftingResultSlotBehaviour() {
    }

    public static CraftingResultSlotBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public void onCrafted(ItemStack craftedItem, EntityHuman human, IInventory craftMatrix) {
        craftedItem.b(human.world, human);

        if (craftedItem.id == Block.WORKBENCH.id) {
            human.a(AchievementList.h, 1);
        } else if (craftedItem.id == Item.WOOD_PICKAXE.id) {
            human.a(AchievementList.i, 1);
        } else if (craftedItem.id == Block.FURNACE.id) {
            human.a(AchievementList.j, 1);
        } else if (craftedItem.id == Item.WOOD_HOE.id) {
            human.a(AchievementList.l, 1);
        } else if (craftedItem.id == Item.BREAD.id) {
            human.a(AchievementList.m, 1);
        } else if (craftedItem.id == Item.CAKE.id) {
            human.a(AchievementList.n, 1);
        } else if (craftedItem.id == Item.STONE_PICKAXE.id) {
            human.a(AchievementList.o, 1);
        } else if (craftedItem.id == Item.WOOD_SWORD.id) {
            human.a(AchievementList.r, 1);
        }

        for (int i = 0; i < craftMatrix.getSize(); ++i) {
            ItemStack ingredient = craftMatrix.getItem(i);

            if (ingredient != null) {
                craftMatrix.splitStack(i, 1);
                if (ingredient.getItem().i()) {
                    craftMatrix.setItem(i, new ItemStack(ingredient.getItem().h()));
                }
            }
        }
    }
}
