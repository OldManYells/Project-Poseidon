package com.legacyminecraft.poseidon.inventory;


public final class DispenserContainerBehaviour {
    private static final DispenserContainerBehaviour INSTANCE = new DispenserContainerBehaviour();

    private DispenserContainerBehaviour() {
    }

    public static DispenserContainerBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeSlots(Container container, IInventory playerInventory, TileEntityDispenser dispenserInventory) {
        int row;
        int column;

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 3; ++column) {
                container.poseidonAddSlot(new Slot(dispenserInventory, column + row * 3, 62 + column * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                container.poseidonAddSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            container.poseidonAddSlot(new Slot(playerInventory, row, 8 + row * 18, 142));
        }
    }

    public boolean canUse(TileEntityDispenser dispenserInventory, EntityHuman human) {
        return dispenserInventory.a_(human);
    }
}
