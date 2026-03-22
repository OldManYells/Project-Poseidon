package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.compat.bukkit.BiomeConversionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.ChunkSnapshotDataAccessBehaviour;
import net.minecraft.server.BiomeBase;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
/**
 * Represents a static, thread-safe snapshot of chunk of blocks
 * Purpose is to allow clean, efficient copy of a chunk data to be made, and then handed off for processing in another thread (e.g. map rendering)
 */
public class CraftChunkSnapshot implements ChunkSnapshot {
    private static final BiomeConversionBehaviour BIOME_CONVERSION_BEHAVIOUR =
            BiomeConversionBehaviour.getInstance();
    private static final ChunkSnapshotDataAccessBehaviour CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR =
            ChunkSnapshotDataAccessBehaviour.getInstance();
    private static final ChunkSnapshotDataAccessBehaviour.BiomeResolver BIOME_RESOLVER =
            new ChunkSnapshotDataAccessBehaviour.BiomeResolver() {
                public Biome resolve(BiomeBase biomeBase) {
                    return BIOME_CONVERSION_BEHAVIOUR.biomeBaseToBiome(biomeBase);
                }
            };

    private final int x, z;
    private final String worldname;
    private final byte[] buf; // Flat buffer in uncompressed chunk file format
    private final byte[] hmap; // Height map
    private final long captureFulltime;
    private final BiomeBase[] biome;
    private final double[] biomeTemp;
    private final double[] biomeRain;

    private static final int BLOCKDATA_OFF = 32768;
    private static final int BLOCKLIGHT_OFF = BLOCKDATA_OFF + 16384;
    private static final int SKYLIGHT_OFF = BLOCKLIGHT_OFF + 16384;

    /**
     * Constructor
     */
    CraftChunkSnapshot(int x, int z, String wname, long wtime, byte[] buf, byte[] hmap, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain) {
        this.x = x;
        this.z = z;
        this.worldname = wname;
        this.captureFulltime = wtime;
        this.buf = buf;
        this.hmap = hmap;
        this.biome = biome;
        this.biomeTemp = biomeTemp;
        this.biomeRain = biomeRain;
    }

    /**
     * Gets the X-coordinate of this chunk
     *
     * @return X-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Z-coordinate of this chunk
     *
     * @return Z-coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Gets name of the world containing this chunk
     *
     * @return Parent World Name
     */
    public String getWorldName() {
        return worldname;
    }

    /**
     * Get block type for block at corresponding coordinate in the chunk
     *
     * @param x 0-15
     * @param y 0-127
     * @param z 0-15
     * @return 0-255
     */
    public int getBlockTypeId(int x, int y, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readBlockTypeId(buf, x, y, z);
    }

    /**
     * Get block data for block at corresponding coordinate in the chunk
     *
     * @param x 0-15
     * @param y 0-127
     * @param z 0-15
     * @return 0-15
     */
    public int getBlockData(int x, int y, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readBlockData(buf, BLOCKDATA_OFF, x, y, z);
    }

    /**
     * Get sky light level for block at corresponding coordinate in the chunk
     *
     * @param x 0-15
     * @param y 0-127
     * @param z 0-15
     * @return 0-15
     */
    public int getBlockSkyLight(int x, int y, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readSkyLight(buf, SKYLIGHT_OFF, x, y, z);
    }

    /**
     * Get light level emitted by block at corresponding coordinate in the chunk
     *
     * @param x 0-15
     * @param y 0-127
     * @param z 0-15
     * @return 0-15
     */
    public int getBlockEmittedLight(int x, int y, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readEmittedLight(buf, BLOCKLIGHT_OFF, x, y, z);
    }

    /**
     * Gets the highest non-air coordinate at the given coordinates
     *
     * @param x X-coordinate of the blocks
     * @param z Z-coordinate of the blocks
     * @return Y-coordinate of the highest non-air block
     */
    public int getHighestBlockYAt(int x, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readHighestBlockY(hmap, x, z);
    }

    /**
     * Get biome at given coordinates
     *
     * @param x X-coordinate
     * @param z Z-coordinate
     * @return Biome at given coordinate
     */
    public Biome getBiome(int x, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readBiome(biome, x, z, BIOME_RESOLVER);
    }

    /**
     * Get raw biome temperature (0.0-1.0) at given coordinate
     *
     * @param x X-coordinate
     * @param z Z-coordinate
     * @return temperature at given coordinate
     */
    public double getRawBiomeTemperature(int x, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readClimateValue(biomeTemp, x, z);
    }

    /**
     * Get raw biome rainfall (0.0-1.0) at given coordinate
     *
     * @param x X-coordinate
     * @param z Z-coordinate
     * @return rainfall at given coordinate
     */
    public double getRawBiomeRainfall(int x, int z) {
        return CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR.readClimateValue(biomeRain, x, z);
    }

    /**
     * Get world full time when chunk snapshot was captured
     * @return time in ticks
     */
    public long getCaptureFullTime() {
        return captureFulltime;
    }
}
