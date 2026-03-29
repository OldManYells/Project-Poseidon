package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class CraftItem extends CraftEntity implements Item {
    private EntityItem item;

    public CraftItem(CraftServer server, EntityItem entity) {
        super(server, entity);
        this.item = entity;
    }

    public ItemStack getItemStack() {
        return new CraftItemStack(item.itemStack);
    }

    public void setItemStack(ItemStack stack) {
        item.itemStack = new org.bukkit.craftbukkit.item.ItemStack(stack.getTypeId(), stack.getAmount(), stack.getDurability());
    }

    @Override
    public String toString() {
        return "CraftItem";
    }

}
