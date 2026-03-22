package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical furnace output merge and input-consumption behaviour.
 */
public final class FurnaceSmeltOutputBehaviour {
    private static final FurnaceSmeltOutputBehaviour INSTANCE = new FurnaceSmeltOutputBehaviour();

    private FurnaceSmeltOutputBehaviour() {
    }

    public static FurnaceSmeltOutputBehaviour getInstance() {
        return INSTANCE;
    }

    public void applySmeltResult(ItemStack[] items, int outputSlot, ItemStack resultStack) {
        if (items[outputSlot] == null) {
            items[outputSlot] = resultStack.cloneItemStack();
        } else if (items[outputSlot].id == resultStack.id && items[outputSlot].damage == resultStack.damage) {
            items[outputSlot].count += resultStack.count;
        }
    }

    public void consumeInput(ItemStack[] items, int inputSlot) {
        --items[inputSlot].count;
        if (items[inputSlot].count <= 0) {
            items[inputSlot] = null;
        }
    }
}
