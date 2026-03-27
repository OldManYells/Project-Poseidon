package com.legacyminecraft.poseidon.inventory;


import java.util.List;

/**
 * Canonical container crafting listener contract bridged by legacy wrappers.
 */
public interface ContainerCraftingListenerContract {
    void onContainerInitialized(Container container, List items);

    void onContainerSlotChanged(Container container, int slotIndex, ItemStack stack);

    void onContainerProgressChanged(Container container, int propertyIndex, int propertyValue);
}
