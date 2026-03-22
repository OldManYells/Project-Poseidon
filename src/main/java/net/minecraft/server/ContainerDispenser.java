package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.DispenserContainerBehaviour;

public class ContainerDispenser extends Container {
    private static final DispenserContainerBehaviour DISPENSER_CONTAINER_BEHAVIOUR = DispenserContainerBehaviour.getInstance();

    private TileEntityDispenser a;

    public ContainerDispenser(IInventory iinventory, TileEntityDispenser tileentitydispenser) {
        this.a = tileentitydispenser;
        DISPENSER_CONTAINER_BEHAVIOUR.initializeSlots(this, iinventory, tileentitydispenser);
    }

    public boolean b(EntityHuman entityhuman) {
        return DISPENSER_CONTAINER_BEHAVIOUR.canUse(this.a, entityhuman);
    }
}
