package com.legacyminecraft.compat.bukkit.inventory;

public class ItemStack extends com.legacyminecraft.compat.bukkit.ItemStack {
    public ItemStack(com.legacyminecraft.compat.bukkit.Material material, int amount) {
        super(material == null ? 0 : material.getId(), amount, 0);
    }

    public ItemStack(com.legacyminecraft.compat.bukkit.Material material, int amount, short durability, byte data) {
        super(material == null ? 0 : material.getId(), amount, durability);
        this.damage = data;
    }

    public ItemStack(int id, int amount, int durability) {
        super(id, amount, durability);
    }
}
