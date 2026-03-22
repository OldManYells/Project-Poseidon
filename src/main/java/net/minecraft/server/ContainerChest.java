package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ChestContainerBehaviour;

public class ContainerChest extends Container {
    private static final ChestContainerBehaviour CHEST_CONTAINER_BEHAVIOUR = ChestContainerBehaviour.getInstance();

    private IInventory a;
    private int b;

    public ContainerChest(IInventory iinventory, IInventory iinventory1) {
        this.a = iinventory1;
        this.b = CHEST_CONTAINER_BEHAVIOUR.initializeSlots(this, iinventory, iinventory1);
    }

    public boolean b(EntityHuman entityhuman) {
        return CHEST_CONTAINER_BEHAVIOUR.canUse(this.a, entityhuman);
    }

    public ItemStack a(int i) {
        return CHEST_CONTAINER_BEHAVIOUR.quickMove(this, this.e, i, this.b);
    }
}
