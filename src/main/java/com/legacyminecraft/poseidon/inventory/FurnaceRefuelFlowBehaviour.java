package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical furnace refuel flow behaviour around event-driven fuel application.
 */
public final class FurnaceRefuelFlowBehaviour {
    private static final FurnaceRefuelFlowBehaviour INSTANCE = new FurnaceRefuelFlowBehaviour();

    private FurnaceRefuelFlowBehaviour() {
    }

    public static FurnaceRefuelFlowBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldAttemptRefuel(int burnTime, boolean canBurn, ItemStack fuelStack) {
        return burnTime <= 0 && canBurn && fuelStack != null;
    }

    public int applyFuelTicks(int burnTime, int fuelTicks) {
        return burnTime + fuelTicks;
    }

    public boolean shouldConsumeFuel(int burnTime, boolean eventBurning) {
        return burnTime > 0 && eventBurning;
    }
}
