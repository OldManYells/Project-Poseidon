package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation chest tile-entity scaffold.
 */
public class TileEntityChest {
    private final ItemStack[] items = new ItemStack[27];

    public int getSize() {
        return items.length;
    }

    public void setItem(int index, ItemStack itemStack) {
        if (index >= 0 && index < items.length) {
            items[index] = itemStack;
        }
    }
}
