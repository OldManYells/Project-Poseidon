package com.legacyminecraft.compat.bukkit;

/**
 * Canonical CraftItemStack scaffold.
 */
public class CraftItemStack extends ItemStack {
    public CraftItemStack(ItemStack itemStack) {
        super(itemStack == null ? 0 : itemStack.id, itemStack == null ? 0 : itemStack.count, itemStack == null ? 0 : itemStack.damage);
    }

    public CraftItemStack(int id, int amount, int durability) {
        super(id, amount, durability);
    }
}
