package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Canonical compat-world view scaffold for migrated Bukkit bridge behaviours.
 */
public class World {
    private final String name;
    private long fullTime;
    public WorldProvider worldProvider = new WorldProvider();
    private final List<BlockPopulator> populators = new ArrayList<BlockPopulator>();
    private boolean keepSpawnInMemory = true;
    private final java.util.Map<String, Byte> blockData = new java.util.HashMap<String, Byte>();
    private final java.util.Map<String, Integer> blockTypeIds = new java.util.HashMap<String, Integer>();

    public World() {
        this("world", 0L);
    }

    public World(String name, long fullTime) {
        this.name = name;
        this.fullTime = fullTime;
    }

    public String getName() {
        return name;
    }

    public long getFullTime() {
        return fullTime;
    }

    public CraftWorld getWorld() {
        return new CraftWorld(this);
    }

    public Server getServer() {
        return new Server();
    }

    public UUID getUID() {
        return new UUID(0L, 0L);
    }

    public Environment getEnvironment() {
        return Environment.NORMAL;
    }

    public WorldServer getHandle() {
        return new WorldServer();
    }

    public List<BlockPopulator> getPopulators() {
        return populators;
    }

    public boolean getKeepSpawnInMemory() {
        return keepSpawnInMemory;
    }

    public void setKeepSpawnInMemory(boolean keepSpawnInMemory) {
        this.keepSpawnInMemory = keepSpawnInMemory;
    }

    public boolean setTypeId(int x, int y, int z, int typeId) {
        blockTypeIds.put(key(x, y, z), typeId);
        return true;
    }

    public boolean setRawTypeId(int x, int y, int z, int typeId) {
        return setTypeId(x, y, z, typeId);
    }

    public boolean setTypeIdAndData(int x, int y, int z, int typeId, byte data) {
        setTypeId(x, y, z, typeId);
        setData(x, y, z, data);
        return true;
    }

    public boolean setRawTypeIdAndData(int x, int y, int z, int typeId, byte data) {
        return setTypeIdAndData(x, y, z, typeId, data);
    }

    public void setData(int x, int y, int z, byte data) {
        blockData.put(key(x, y, z), data);
    }

    public void setRawData(int x, int y, int z, byte data) {
        setData(x, y, z, data);
    }

    public void notify(int x, int y, int z) {
    }

    public Location getSpawnLocation() {
        return new Location(this, 0.0D, 64.0D, 0.0D);
    }

    public Block getBlockAt(int x, int y, int z) {
        Block block = new Block(this, x, y, z);
        Integer typeId = blockTypeIds.get(key(x, y, z));
        if (typeId != null) {
            block.setTypeId(typeId);
        }
        Byte data = blockData.get(key(x, y, z));
        if (data != null) {
            block.setData(data);
        }
        return block;
    }

    private String key(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }

    public boolean isBlockPowered(int x, int y, int z) {
        return false;
    }

    public boolean isBlockIndirectlyPowered(int x, int y, int z) {
        return false;
    }

    public boolean isBlockFacePowered(int x, int y, int z, int face) {
        return false;
    }

    public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
        return false;
    }

    public void playNote(int x, int y, int z, byte instrument, byte note) {
    }

    public enum Environment {
        NORMAL(0),
        NETHER(-1),
        SKY(1);

        private final int id;

        Environment(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Environment getEnvironment(int id) {
            if (id == -1) {
                return NETHER;
            }
            if (id == 1) {
                return SKY;
            }
            return NORMAL;
        }
    }
}
