package com.legacyminecraft.poseidon.world.player;

import com.legacyminecraft.poseidon.event.PlayerDeathEvent;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet3Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical handler for player death event, drop, and keep-inventory flow.
 */
public final class PlayerDeathHandlingSystem {
    private static final PlayerDeathHandlingSystem INSTANCE = new PlayerDeathHandlingSystem();

    private PlayerDeathHandlingSystem() {
    }

    public static PlayerDeathHandlingSystem getInstance() {
        return INSTANCE;
    }

    public void handleDeath(EntityPlayer player) {
        List<org.bukkit.inventory.ItemStack> loot = collectLoot(player.inventory.items, player.inventory.armor);

        org.bukkit.entity.Entity bukkitEntity = player.getBukkitEntity();
        PlayerDeathEvent event = new PlayerDeathEvent(bukkitEntity, loot);
        player.world.getServer().getPluginManager().callEvent(event);

        if (shouldBroadcastDeathMessage(event.getDeathMessage())) {
            player.b.serverConfigurationManager.sendAll(new Packet3Chat(event.getDeathMessage()));
        }

        clearInventoryIfNeeded(event.getKeepInventory(), player.inventory.items, player.inventory.armor);

        for (org.bukkit.inventory.ItemStack stack : event.getDrops()) {
            player.world.getWorld().dropItemNaturally(bukkitEntity.getLocation(), stack);
        }

        player.y();
    }

    public List<org.bukkit.inventory.ItemStack> collectLoot(ItemStack[] inventoryItems, ItemStack[] armorItems) {
        List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        addLoot(loot, inventoryItems);
        addLoot(loot, armorItems);
        return loot;
    }

    public boolean shouldBroadcastDeathMessage(String deathMessage) {
        return deathMessage != null && !deathMessage.trim().isEmpty();
    }

    public void clearInventoryIfNeeded(boolean keepInventory, ItemStack[] inventoryItems, ItemStack[] armorItems) {
        if (keepInventory) {
            return;
        }

        clearItems(inventoryItems);
        clearItems(armorItems);
    }

    private void addLoot(List<org.bukkit.inventory.ItemStack> loot, ItemStack[] items) {
        for (int index = 0; index < items.length; ++index) {
            if (items[index] != null) {
                loot.add(new org.bukkit.inventory.ItemStack(items[index].id, items[index].count, (short) items[index].damage));
            }
        }
    }

    private void clearItems(ItemStack[] items) {
        for (int index = 0; index < items.length; ++index) {
            items[index] = null;
        }
    }
}
