package com.legacyminecraft.compat.bukkit;

import java.util.List;

/**
 * Canonical CraftWorld scaffold for migrated explosion bridge behaviour.
 */
public class CraftWorld {
    private final World handle;

    public CraftWorld() {
        this(new World());
    }

    public CraftWorld(World handle) {
        this.handle = handle;
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        return true;
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, EntityDamageEvent.DamageCause cause) {
        return true;
    }

    public boolean createExplosion(Location location, float power, boolean setFire) {
        return createExplosion(location.getX(), location.getY(), location.getZ(), power, setFire);
    }

    public boolean setSpawnLocation(int x, int y, int z, float yaw, float pitch) {
        return true;
    }

    public com.legacyminecraft.compat.bukkit.block.Block getBlockAt(int x, int y, int z) {
        return new com.legacyminecraft.compat.bukkit.block.Block();
    }

    public Object getTileEntityAt(int x, int y, int z) {
        return null;
    }

    public List<BlockPopulator> getPopulators() {
        return handle.getPopulators();
    }

    public boolean getKeepSpawnInMemory() {
        return handle.getKeepSpawnInMemory();
    }

    public World getHandle() {
        return handle;
    }

    public void loadChunk(int x, int z) {
    }

    public Chunk getChunkAt(int x, int z) {
        return new CraftChunk(new Chunk(new WorldServer(), new byte[0], x, z));
    }

    public int getHighestBlockYAt(int x, int z) {
        return 0;
    }

    public int getBlockTypeIdAt(int x, int y, int z) {
        return 0;
    }

    public Block getHighestBlockAt(int x, int z) {
        return getBlockAt(x, getHighestBlockYAt(x, z), z);
    }

    public boolean isChunkLoaded(int x, int z) {
        return false;
    }

    public boolean unloadChunk(int x, int z) {
        return true;
    }

    public boolean unloadChunk(int x, int z, boolean save) {
        return true;
    }

    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        return true;
    }

    public boolean unloadChunkRequest(int x, int z, boolean safe) {
        return true;
    }

    public void playEffect(Location location, Effect effect, int data, int radius) {
    }

    public Item dropItemNaturally(Location location, com.legacyminecraft.compat.bukkit.inventory.ItemStack itemStack) {
        int itemId = itemStack == null ? 0 : itemStack.getTypeId();
        return new Item(itemId, "DROPPED_ITEM");
    }

    public String getName() {
        return handle.getName();
    }

    public long getFullTime() {
        return handle.getFullTime();
    }
}
