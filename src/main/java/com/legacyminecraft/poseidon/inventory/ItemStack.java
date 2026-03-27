package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local alias to the compat stack model.
 */
public class ItemStack extends com.legacyminecraft.compat.bukkit.ItemStack {
    public ItemStack(int id, int count, int damage) {
        super(id, count, damage);
    }

    public ItemStack(com.legacyminecraft.poseidon.item.Item item) {
        super(item);
    }

    public ItemStack(com.legacyminecraft.poseidon.nbt.NBTTagCompound tag) {
        super(tag);
    }

    public static boolean equals(ItemStack left, ItemStack right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.count == right.count && left.id == right.id && left.damage == right.damage;
    }

    public ItemStack a(int amount) {
        com.legacyminecraft.compat.bukkit.ItemStack split = super.a(amount);
        if (split == null) {
            return null;
        }
        return new ItemStack(split.id, split.count, split.damage);
    }
}
