package com.legacyminecraft.poseidon.world.player;


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
        List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> loot = collectLoot(player.inventory.items, player.inventory.armor);

        com.legacyminecraft.compat.bukkit.Entity bukkitEntity = player.getBukkitEntity();
        PlayerDeathEvent event = new PlayerDeathEvent(bukkitEntity, loot);

        if (shouldBroadcastDeathMessage(event.getDeathMessage())) {
            broadcastDeathMessage(player, event.getDeathMessage());
        }

        clearInventoryIfNeeded(event.getKeepInventory(), player.inventory.items, player.inventory.armor);

        for (com.legacyminecraft.compat.bukkit.inventory.ItemStack stack : event.getDrops()) {
            player.world.getWorld().dropItemNaturally(bukkitEntity.getLocation(), stack);
        }

        try {
            player.getClass().getMethod("y").invoke(player);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> collectLoot(
            com.legacyminecraft.compat.bukkit.ItemStack[] inventoryItems,
            com.legacyminecraft.compat.bukkit.ItemStack[] armorItems
    ) {
        List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> loot = new ArrayList<com.legacyminecraft.compat.bukkit.inventory.ItemStack>();
        addLoot(loot, inventoryItems);
        addLoot(loot, armorItems);
        return loot;
    }

    public boolean shouldBroadcastDeathMessage(String deathMessage) {
        return deathMessage != null && !deathMessage.trim().isEmpty();
    }

    public void clearInventoryIfNeeded(
            boolean keepInventory,
            com.legacyminecraft.compat.bukkit.ItemStack[] inventoryItems,
            com.legacyminecraft.compat.bukkit.ItemStack[] armorItems
    ) {
        if (keepInventory) {
            return;
        }

        clearItems(inventoryItems);
        clearItems(armorItems);
    }

    private void addLoot(
            List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> loot,
            com.legacyminecraft.compat.bukkit.ItemStack[] items
    ) {
        for (int index = 0; index < items.length; ++index) {
            if (items[index] != null) {
                loot.add(new com.legacyminecraft.compat.bukkit.inventory.ItemStack(items[index].id, items[index].count, (short) items[index].damage));
            }
        }
    }

    private void clearItems(com.legacyminecraft.compat.bukkit.ItemStack[] items) {
        for (int index = 0; index < items.length; ++index) {
            items[index] = null;
        }
    }

    private void broadcastDeathMessage(EntityPlayer player, String message) {
        if (player == null || player.world == null) {
            return;
        }
        try {
            Object server = player.world.getServer();
            Object manager = server.getClass().getField("serverConfigurationManager").get(server);
            manager.getClass().getMethod("sendAll", Object.class).invoke(manager, new Packet3Chat(message));
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
