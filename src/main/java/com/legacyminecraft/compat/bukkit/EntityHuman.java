package com.legacyminecraft.compat.bukkit;

import com.legacyminecraft.poseidon.world.ChunkCoordinates;

/**
 * Canonical compat human-entity scaffold.
 */
public class EntityHuman extends EntityLiving implements Human {
    public final InventoryPlayer inventory = new InventoryPlayer();
    public int dimension;

    public void a(Object statistic, int amount) {
    }

    public void b(int healAmount) {
    }

    public void b(ItemStack itemStack) {
    }

    public static ChunkCoordinates getBed(WorldServer worldServer, ChunkCoordinates bedCoordinates) {
        return bedCoordinates;
    }
}
