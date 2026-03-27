package com.legacyminecraft.compat.bukkit;

import com.legacyminecraft.poseidon.nbt.NBTTagCompound;

/**
 * Canonical compat item-stack scaffold.
 */
public class ItemStack extends com.legacyminecraft.poseidon.item.ItemStack {
    public ItemStack(com.legacyminecraft.poseidon.item.Item item) {
        this(item.id, 1, 0);
    }

    public ItemStack(int id, int count, int damage) {
        super(id, count, damage);
    }

    public ItemStack(NBTTagCompound tag) {
        this(tag.e("id"), tag.c("Count"), tag.d("Damage"));
    }

    public int getTypeId() {
        return this.id;
    }

    public Material getType() {
        return Material.getMaterial(this.id);
    }

    public int getAmount() {
        return this.count;
    }

    public void setAmount(int amount) {
        this.count = amount;
    }

    public short getDurability() {
        return (short) this.damage;
    }

    public void setDurability(short durability) {
        this.damage = durability;
    }

    public com.legacyminecraft.compat.bukkit.inventory.ItemStack toBukkitInventoryItemStack() {
        return new com.legacyminecraft.compat.bukkit.inventory.ItemStack(this.id, this.count, this.damage);
    }

    public ItemStack a(int amount) {
        int extracted = Math.min(amount, this.count);
        this.count -= extracted;
        return new ItemStack(this.id, extracted, this.damage);
    }

    public NBTTagCompound a(NBTTagCompound tag) {
        tag.a("id", (short) this.id);
        tag.a("Count", (byte) this.count);
        tag.a("Damage", (short) this.damage);
        return tag;
    }

    public boolean isStackable() {
        return getMaxStackSize() > 1 && !f();
    }

    public boolean usesData() {
        com.legacyminecraft.poseidon.item.Item item = getItem();
        return item != null && item.d();
    }

    public boolean c(ItemStack other) {
        return other != null && this.id == other.id && this.count == other.count && this.damage == other.damage;
    }

    @Override
    public ItemStack cloneItemStack() {
        return new ItemStack(this.id, this.count, this.damage);
    }

    public static ItemStack b(ItemStack stack) {
        return stack == null ? null : stack.cloneItemStack();
    }

    public static boolean equals(ItemStack left, ItemStack right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.id == right.id && left.count == right.count && left.damage == right.damage;
    }
}
