package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local item-stack alias.
 */
public class ItemStack extends com.legacyminecraft.compat.bukkit.ItemStack {
    public ItemStack(com.legacyminecraft.poseidon.item.Item item) {
        super(item);
    }

    public ItemStack(com.legacyminecraft.poseidon.block.Block block) {
        super(block.id, 1, 0);
    }

    public ItemStack(int id, int count, int damage) {
        super(id, count, damage);
    }

    public ItemStack(NBTTagCompound tag) {
        super(tag);
    }

    public void damage(int amount, EntityHuman player) {
        this.damage += amount;
    }
}
