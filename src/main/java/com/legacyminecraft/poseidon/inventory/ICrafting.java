package com.legacyminecraft.poseidon.inventory;

import java.util.List;

/**
 * Inventory-local crafting listener contract.
 */
public interface ICrafting {
    void a(Container container, List items);

    void a(Container container, int slot, ItemStack itemStack);

    void a(Container container, int property, int value);
}
