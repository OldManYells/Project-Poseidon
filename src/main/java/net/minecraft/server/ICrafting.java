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

    @Override
    default void onContainerInitialized(com.legacyminecraft.poseidon.inventory.Container container, List items) {
        this.a((Container) (Object) container, items);
    }

    @Override
    default void onContainerSlotChanged(com.legacyminecraft.poseidon.inventory.Container container, int slotIndex,
                                        com.legacyminecraft.poseidon.inventory.ItemStack stack) {
        this.a((Container) (Object) container, slotIndex, (ItemStack) (Object) stack);
    }

    @Override
    default void onContainerProgressChanged(com.legacyminecraft.poseidon.inventory.Container container, int propertyIndex,
                                            int propertyValue) {
        this.a((Container) (Object) container, propertyIndex, propertyValue);
    }
}
