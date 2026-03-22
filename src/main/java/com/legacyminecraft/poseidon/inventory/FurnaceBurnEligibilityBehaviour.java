package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.ItemStack;

/**
 * Canonical furnace smelt eligibility behaviour.
 */
public final class FurnaceBurnEligibilityBehaviour {
    private static final FurnaceBurnEligibilityBehaviour INSTANCE = new FurnaceBurnEligibilityBehaviour();

    private FurnaceBurnEligibilityBehaviour() {
    }

    public static FurnaceBurnEligibilityBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canBurn(ItemStack inputStack, ItemStack outputStack, int maxInventoryStackSize) {
        if (inputStack == null) {
            return false;
        }

        ItemStack smeltingResult = FurnaceRecipes.getInstance().a(inputStack.getItem().id);
        if (smeltingResult == null) {
            return false;
        }

        if (outputStack == null) {
            return true;
        }

        if (!outputStack.doMaterialsMatch(smeltingResult)) {
            return false;
        }

        if (outputStack.count + smeltingResult.count <= maxInventoryStackSize && outputStack.count < outputStack.getMaxStackSize()) {
            return true;
        }

        return outputStack.count + smeltingResult.count <= smeltingResult.getMaxStackSize();
    }
}
