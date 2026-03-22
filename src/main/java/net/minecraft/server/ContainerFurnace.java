package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.FurnaceContainerBehaviour;

public class ContainerFurnace extends Container {
    private static final FurnaceContainerBehaviour FURNACE_CONTAINER_BEHAVIOUR = FurnaceContainerBehaviour.getInstance();

    private TileEntityFurnace a;
    private int b = 0;
    private int c = 0;
    private int h = 0;

    public ContainerFurnace(InventoryPlayer inventoryplayer, TileEntityFurnace tileentityfurnace) {
        this.a = tileentityfurnace;
        FURNACE_CONTAINER_BEHAVIOUR.initializeSlots(this, inventoryplayer, tileentityfurnace);
    }

    public void a(ICrafting icrafting) {
        super.a(icrafting);
        FURNACE_CONTAINER_BEHAVIOUR.sendInitialProgress(this, icrafting, this.a);
    }

    public void a() {
        super.a();
        FurnaceContainerBehaviour.ProgressState state = FURNACE_CONTAINER_BEHAVIOUR.broadcastProgress(this, this.listeners, this.a, this.b, this.c, this.h);
        this.b = state.cookTime;
        this.c = state.burnTime;
        this.h = state.fuelTicks;
    }

    public boolean b(EntityHuman entityhuman) {
        return FURNACE_CONTAINER_BEHAVIOUR.canUse(this.a, entityhuman);
    }

    public ItemStack a(int i) {
        return FURNACE_CONTAINER_BEHAVIOUR.quickMove(this, this.e, i);
    }
}
