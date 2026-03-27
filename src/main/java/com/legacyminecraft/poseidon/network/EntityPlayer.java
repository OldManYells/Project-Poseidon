package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.compat.bukkit.InventoryPlayer;
import com.legacyminecraft.compat.bukkit.NetServerHandler;
import com.legacyminecraft.compat.bukkit.WorldServer;

/**
 * Network-local player-entity scaffold.
 */
public class EntityPlayer extends com.legacyminecraft.poseidon.entity.EntityPlayer {
    public final NetServerHandler netServerHandler = new NetServerHandler();
    public final InventoryPlayer inventory = new InventoryPlayer();
    public final com.legacyminecraft.poseidon.inventory.Container defaultContainer =
            new com.legacyminecraft.poseidon.inventory.Container();
    public com.legacyminecraft.poseidon.inventory.Container activeContainer = defaultContainer;
    public final com.legacyminecraft.compat.bukkit.ItemInWorldManager itemInWorldManager =
            new com.legacyminecraft.compat.bukkit.ItemInWorldManager();
    public WorldServer world = new WorldServer();
    public boolean dead;
    public int id;
    public String spawnWorld = "world";
    public int health = 20;
    public int fireTicks;

    public com.legacyminecraft.compat.bukkit.Entity getBukkitEntity() {
        return new com.legacyminecraft.compat.bukkit.entity.EntityPlayer();
    }

    public void w() {
    }

    public void a(boolean a, boolean b, boolean c) {
    }

    public void a(boolean resetFallDistance) {
    }

    public void syncInventory() {
    }

    public void updateInventory(Object container) {
    }

    public void C() {
    }

    public boolean isSleeping() {
        return super.isSleeping();
    }

    public com.legacyminecraft.poseidon.world.ChunkCoordinates getBed() {
        return null;
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.boundingBox.minX = x - 0.30000001192092896D;
        this.boundingBox.minY = y;
        this.boundingBox.minZ = z - 0.30000001192092896D;
        this.boundingBox.maxX = x + 0.30000001192092896D;
        this.boundingBox.maxY = y + (double) this.height;
        this.boundingBox.maxZ = z + 0.30000001192092896D;
    }

    public void move(double deltaX, double deltaY, double deltaZ) {
        this.locX += deltaX;
        this.locY += deltaY;
        this.locZ += deltaZ;
        this.boundingBox = this.boundingBox.b(deltaX, deltaY, deltaZ);
    }

    public long getPlayerTime() {
        return 0L;
    }
}
