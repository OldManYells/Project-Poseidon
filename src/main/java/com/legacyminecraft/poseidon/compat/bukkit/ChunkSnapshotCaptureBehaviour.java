package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.Chunk;
import net.minecraft.server.WorldChunkManager;

/**
 * Canonical behavior for CraftChunk snapshot data capture and biome/climate copy orchestration.
 */
public final class ChunkSnapshotCaptureBehaviour {
    private static final ChunkSnapshotCaptureBehaviour INSTANCE = new ChunkSnapshotCaptureBehaviour();

    private static final int FULL_CHUNK_BUFFER_SIZE = 32768 + 16384 + 16384 + 16384;

    private ChunkSnapshotCaptureBehaviour() {
    }

    public static ChunkSnapshotCaptureBehaviour getInstance() {
        return INSTANCE;
    }

    public SnapshotData captureFromChunk(
            Chunk chunk,
            int chunkX,
            int chunkZ,
            boolean includeMaxBlockY,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        byte[] chunkBuffer = new byte[FULL_CHUNK_BUFFER_SIZE];
        chunk.getData(chunkBuffer, 0, 0, 0, 16, 128, 16, 0);

        byte[] heightMap = includeMaxBlockY ? copyHeightMap(chunk.heightMap) : null;
        BiomeCapture biomeCapture = captureBiomeData(
                chunk.world.getWorldChunkManager(),
                chunkX,
                chunkZ,
                includeBiome,
                includeBiomeTempRain
        );

        return new SnapshotData(
                chunkBuffer,
                heightMap,
                biomeCapture.getBiomes(),
                biomeCapture.getTemperatures(),
                biomeCapture.getRainfall()
        );
    }

    public SnapshotData captureEmpty(
            WorldChunkManager worldChunkManager,
            int chunkX,
            int chunkZ,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        BiomeCapture biomeCapture = captureBiomeData(worldChunkManager, chunkX, chunkZ, includeBiome, includeBiomeTempRain);
        return new SnapshotData(
                null,
                null,
                biomeCapture.getBiomes(),
                biomeCapture.getTemperatures(),
                biomeCapture.getRainfall()
        );
    }

    private byte[] copyHeightMap(byte[] sourceHeightMap) {
        byte[] copiedHeightMap = new byte[256];
        System.arraycopy(sourceHeightMap, 0, copiedHeightMap, 0, copiedHeightMap.length);
        return copiedHeightMap;
    }

    private BiomeCapture captureBiomeData(
            WorldChunkManager worldChunkManager,
            int chunkX,
            int chunkZ,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        if (!includeBiome && !includeBiomeTempRain) {
            return BiomeCapture.empty();
        }

        BiomeBase[] biomeBase = worldChunkManager.getBiomeData(chunkX << 4, chunkZ << 4, 16, 16);

        BiomeBase[] biomes = null;
        if (includeBiome) {
            biomes = new BiomeBase[256];
            System.arraycopy(biomeBase, 0, biomes, 0, biomes.length);
        }

        double[] temperatures = null;
        double[] rainfall = null;
        if (includeBiomeTempRain) {
            temperatures = new double[256];
            rainfall = new double[256];
            System.arraycopy(worldChunkManager.temperature, 0, temperatures, 0, temperatures.length);
            System.arraycopy(worldChunkManager.rain, 0, rainfall, 0, rainfall.length);
        }

        return new BiomeCapture(biomes, temperatures, rainfall);
    }

    public static final class SnapshotData {
        private final byte[] chunkBuffer;
        private final byte[] heightMap;
        private final BiomeBase[] biomes;
        private final double[] temperatures;
        private final double[] rainfall;

        public SnapshotData(byte[] chunkBuffer, byte[] heightMap, BiomeBase[] biomes, double[] temperatures, double[] rainfall) {
            this.chunkBuffer = chunkBuffer;
            this.heightMap = heightMap;
            this.biomes = biomes;
            this.temperatures = temperatures;
            this.rainfall = rainfall;
        }

        public byte[] getChunkBuffer() {
            return chunkBuffer;
        }

        public byte[] getHeightMap() {
            return heightMap;
        }

        public BiomeBase[] getBiomes() {
            return biomes;
        }

        public double[] getTemperatures() {
            return temperatures;
        }

        public double[] getRainfall() {
            return rainfall;
        }
    }

    private static final class BiomeCapture {
        private final BiomeBase[] biomes;
        private final double[] temperatures;
        private final double[] rainfall;

        private BiomeCapture(BiomeBase[] biomes, double[] temperatures, double[] rainfall) {
            this.biomes = biomes;
            this.temperatures = temperatures;
            this.rainfall = rainfall;
        }

        private static BiomeCapture empty() {
            return new BiomeCapture(null, null, null);
        }

        public BiomeBase[] getBiomes() {
            return biomes;
        }

        public double[] getTemperatures() {
            return temperatures;
        }

        public double[] getRainfall() {
            return rainfall;
        }
    }
}

