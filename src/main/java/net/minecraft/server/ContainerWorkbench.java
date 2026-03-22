package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.WorkbenchContainerBehaviour;

public class ContainerWorkbench extends Container {
    private static final WorkbenchContainerBehaviour WORKBENCH_CONTAINER_BEHAVIOUR = WorkbenchContainerBehaviour.getInstance();

    public InventoryCrafting craftInventory = WORKBENCH_CONTAINER_BEHAVIOUR.createCraftInventory(this);
    public IInventory resultInventory = WORKBENCH_CONTAINER_BEHAVIOUR.createResultInventory();
    private World c;
    private int h;
    private int i;
    private int j;

    public ContainerWorkbench(InventoryPlayer inventoryplayer, World world, int i, int j, int k) {
        this.c = world;
        this.h = i;
        this.i = j;
        this.j = k;
        WORKBENCH_CONTAINER_BEHAVIOUR.initializeSlots(this, inventoryplayer, this.craftInventory, this.resultInventory);

        this.a((IInventory) this.craftInventory);
    }

    public void a(IInventory iinventory) {
        WORKBENCH_CONTAINER_BEHAVIOUR.updateCraftResult(this, this.craftInventory, this.resultInventory, super.listeners);
    }

    public void a(EntityHuman entityhuman) {
        super.a(entityhuman);
        WORKBENCH_CONTAINER_BEHAVIOUR.onClose(this, entityhuman, this.c, this.craftInventory);
    }

    public boolean b(EntityHuman entityhuman) {
        return WORKBENCH_CONTAINER_BEHAVIOUR.canUse(this.c, this.h, this.i, this.j, entityhuman);
    }

    public ItemStack a(int i) {
        return WORKBENCH_CONTAINER_BEHAVIOUR.quickMove(this, this.e, i);
    }
}
