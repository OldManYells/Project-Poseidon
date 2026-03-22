package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ContainerCraftingListenerContract;

import java.util.List;

public interface ICrafting extends ContainerCraftingListenerContract {

    void a(Container container, List list);

    void a(Container container, int i, ItemStack itemstack);

    void a(Container container, int i, int j);

    default void onContainerInitialized(Container container, List items) {
        this.a(container, items);
    }

    default void onContainerSlotChanged(Container container, int slotIndex, ItemStack stack) {
        this.a(container, slotIndex, stack);
    }

    default void onContainerProgressChanged(Container container, int propertyIndex, int propertyValue) {
        this.a(container, propertyIndex, propertyValue);
    }
}
