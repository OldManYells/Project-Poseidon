package org.bukkit.craftbukkit;

import com.legacyminecraft.compat.bukkit.BiomeConversionBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotBlockLookupBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotDataAccessBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotBiomeLookupBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotClimateLookupBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkSnapshotFactoryBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotPackedBufferLayoutBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotMetadataAccessBehaviour;
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
    private static final ChunkSnapshotBlockLookupBehaviour CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR =
            ChunkSnapshotBlockLookupBehaviour.getInstance();
    private static final ChunkSnapshotBiomeLookupBehaviour CHUNK_SNAPSHOT_BIOME_LOOKUP_BEHAVIOUR =
            ChunkSnapshotBiomeLookupBehaviour.getInstance();
    private static final ChunkSnapshotClimateLookupBehaviour CHUNK_SNAPSHOT_CLIMATE_LOOKUP_BEHAVIOUR =
            ChunkSnapshotClimateLookupBehaviour.getInstance();
    private static final ChunkSnapshotPackedBufferLayoutBehaviour CHUNK_SNAPSHOT_PACKED_BUFFER_LAYOUT_BEHAVIOUR =
            ChunkSnapshotPackedBufferLayoutBehaviour.getInstance();
    private static final ChunkSnapshotMetadataAccessBehaviour CHUNK_SNAPSHOT_METADATA_ACCESS_BEHAVIOUR =
            ChunkSnapshotMetadataAccessBehaviour.getInstance();
    private static final CraftChunkSnapshotFactoryBehaviour CRAFT_CHUNK_SNAPSHOT_FACTORY_BEHAVIOUR =
            CraftChunkSnapshotFactoryBehaviour.getInstance();

    private final int x, z;
    private final String worldname;
    private final byte[] buf; // Flat buffer in uncompressed chunk file format
    private final byte[] hmap; // Height map
    private final long captureFulltime;
    private final BiomeBase[] biome;
    private final double[] biomeTemp;
    private final double[] biomeRain;

    /**
     * Constructor
     */
    public CraftChunkSnapshot(int x, int z, String wname, long wtime, byte[] buf, byte[] hmap, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain) {
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

    public static CraftChunkSnapshot create(
            int x,
            int z,
            String worldName,
            long worldTime,
            byte[] chunkBuffer,
            byte[] heightMap,
            BiomeBase[] biomeData,
            double[] biomeTemperature,
            double[] biomeRainfall
    ) {
        return CRAFT_CHUNK_SNAPSHOT_FACTORY_BEHAVIOUR.createSnapshot(
                x,
                z,
                worldName,
                worldTime,
                chunkBuffer,
                heightMap,
                biomeData,
                biomeTemperature,
                biomeRainfall
        );
    }

    /**
     * Gets the X-coordinate of this chunk
     *
     * @return X-coordinate
     */
    public int getX() {
        return CHUNK_SNAPSHOT_METADATA_ACCESS_BEHAVIOUR.getX(x);
    }

    /**
     * Gets the Z-coordinate of this chunk
     *
     * @return Z-coordinate
     */
    public int getZ() {
        return CHUNK_SNAPSHOT_METADATA_ACCESS_BEHAVIOUR.getZ(z);
    }

    /**
     * Gets name of the world containing this chunk
     *
     * @return Parent World Name
     */
    public String getWorldName() {
        return CHUNK_SNAPSHOT_METADATA_ACCESS_BEHAVIOUR.getWorldName(worldname);
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
        return CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR.getBlockTypeId(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, buf, x, y, z);
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
        return CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR.getBlockData(
                CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR,
                buf,
                CHUNK_SNAPSHOT_PACKED_BUFFER_LAYOUT_BEHAVIOUR.getBlockDataOffset(),
                x,
                y,
                z
        );
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
        return CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR.getSkyLight(
                CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR,
                buf,
                CHUNK_SNAPSHOT_PACKED_BUFFER_LAYOUT_BEHAVIOUR.getSkyLightOffset(),
                x,
                y,
                z
        );
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
        return CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR.getEmittedLight(
                CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR,
                buf,
                CHUNK_SNAPSHOT_PACKED_BUFFER_LAYOUT_BEHAVIOUR.getBlockLightOffset(),
                x,
                y,
                z
        );
    }

    /**
     * Gets the highest non-air coordinate at the given coordinates
     *
     * @param x X-coordinate of the blocks
     * @param z Z-coordinate of the blocks
     * @return Y-coordinate of the highest non-air block
     */
    public int getHighestBlockYAt(int x, int z) {
        return CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR.getHighestBlockY(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, hmap, x, z);
    }

    /**
     * Get biome at given coordinates
     *
     * @param x X-coordinate
     * @param z Z-coordinate
     * @return Biome at given coordinate
     */
    public Biome getBiome(int x, int z) {
        return CHUNK_SNAPSHOT_BIOME_LOOKUP_BEHAVIOUR.getBiome(
                CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR,
                biome,
                x,
                z,
                BIOME_CONVERSION_BEHAVIOUR
        );
    }

    /**
     * Get raw biome temperature (0.0-1.0) at given coordinate
     *
     * @param x X-coordinate
     * @param z Z-coordinate
     * @return temperature at given coordinate
     */
    public double getRawBiomeTemperature(int x, int z) {
        return CHUNK_SNAPSHOT_CLIMATE_LOOKUP_BEHAVIOUR
                .getTemperature(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, biomeTemp, x, z);
    }

    /**
     * Get raw biome rainfall (0.0-1.0) at given coordinate
     *
     * @param x X-coordinate
     * @param z Z-coordinate
     * @return rainfall at given coordinate
     */
    public double getRawBiomeRainfall(int x, int z) {
        return CHUNK_SNAPSHOT_CLIMATE_LOOKUP_BEHAVIOUR
                .getRainfall(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, biomeRain, x, z);
    }

    /**
     * Get world full time when chunk snapshot was captured
     * @return time in ticks
     */
    public long getCaptureFullTime() {
        return CHUNK_SNAPSHOT_METADATA_ACCESS_BEHAVIOUR.getCaptureFullTime(captureFulltime);
    }
}
