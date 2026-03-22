package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityItem;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Canonical bridge for CraftItem stack conversion between Bukkit and NMS item entities.
 */
public final class ItemEntityStackBridgeBehaviour {
    private static final ItemEntityStackBridgeBehaviour INSTANCE = new ItemEntityStackBridgeBehaviour();

    private ItemEntityStackBridgeBehaviour() {
    }

    public static ItemEntityStackBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack toBukkitItemStack(EntityItem itemEntity) {
        return new CraftItemStack(itemEntity.itemStack);
    }

    public void applyBukkitItemStack(EntityItem itemEntity, ItemStack stack) {
        itemEntity.itemStack = new net.minecraft.server.ItemStack(stack.getTypeId(), stack.getAmount(), stack.getDurability());
    }
}

