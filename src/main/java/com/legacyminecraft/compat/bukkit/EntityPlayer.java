package com.legacyminecraft.compat.bukkit;

import com.legacyminecraft.poseidon.inventory.Container;
import com.legacyminecraft.poseidon.inventory.InventoryPlayer;
import com.legacyminecraft.poseidon.world.ChunkCoordinates;

/**
 * Canonical compat player-entity scaffold.
 */
public class EntityPlayer extends EntityHuman implements Player {
    public final NetServerHandler netServerHandler = new NetServerHandler();
    public final InventoryPlayer inventory = new InventoryPlayer();
    public final ItemInWorldManager itemInWorldManager = new ItemInWorldManager();
    public boolean fauxSleeping;
    public long timeOffset;
    public boolean relativeTime;
    public Container activeContainer = new Container();
    public Container defaultContainer = new Container();
    public String name = "player";
    public boolean h;
    public String spawnWorld = "world";
    private boolean sneaking;

    public void setSneak(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void updateInventory(Object container) {
    }

    public long getPlayerTime() {
        return timeOffset;
    }

    public void F() {
    }

    public void a(boolean value) {
    }

    public void c(Entity entity) {
    }

    public void d(Entity entity) {
    }

    public boolean e(Entity entity) {
        return true;
    }

    public double g(Entity entity) {
        return 0.0D;
    }

    public ItemStack c_(int slot) {
        if (slot >= 0 && slot < inventory.items.length) {
            return inventory.items[slot];
        }
        return null;
    }

    public ChunkCoordinates getBed() {
        return null;
    }

    public void syncInventory() {
    }

    public void C() {
    }
}
