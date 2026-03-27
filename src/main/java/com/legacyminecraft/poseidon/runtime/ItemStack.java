package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local item stack alias.
 */
public class ItemStack extends com.legacyminecraft.compat.bukkit.ItemStack {
    public ItemStack(int id, int count, int damage) {
        super(id, count, damage);
    }
}
