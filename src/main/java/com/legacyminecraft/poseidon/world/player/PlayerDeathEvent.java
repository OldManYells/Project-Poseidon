package com.legacyminecraft.poseidon.world.player;

import com.legacyminecraft.compat.bukkit.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * World-player local death-event data carrier used by the migrated death flow.
 */
public class PlayerDeathEvent {
    private final Entity entity;
    private final List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> drops;
    private String deathMessage = "";
    private boolean keepInventory;

    public PlayerDeathEvent(
            Entity entity,
            List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> drops
    ) {
        this.entity = entity;
        this.drops = new ArrayList<com.legacyminecraft.compat.bukkit.inventory.ItemStack>(drops);
    }

    public Entity getEntity() {
        return entity;
    }

    public List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> getDrops() {
        return drops;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage == null ? "" : deathMessage;
    }

    public boolean getKeepInventory() {
        return keepInventory;
    }

    public void setKeepInventory(boolean keepInventory) {
        this.keepInventory = keepInventory;
    }
}
