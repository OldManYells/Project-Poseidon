package com.legacyminecraft.compat.bukkit;

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
            Object chunk,
            int chunkX,
            int chunkZ,
            boolean includeMaxBlockY,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        byte[] chunkBuffer = new byte[FULL_CHUNK_BUFFER_SIZE];
        invokeChunkDataCopy(chunk, chunkBuffer);

        byte[] heightMap = includeMaxBlockY ? copyHeightMap(readHeightMap(chunk)) : null;
        BiomeCapture biomeCapture = captureBiomeData(
                resolveWorldChunkManager(chunk),
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
            net.minecraft.server.WorldChunkManager worldChunkManager,
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
        if (sourceHeightMap != null) {
            System.arraycopy(sourceHeightMap, 0, copiedHeightMap, 0, Math.min(copiedHeightMap.length, sourceHeightMap.length));
        }
        return copiedHeightMap;
    }

    private BiomeCapture captureBiomeData(
            net.minecraft.server.WorldChunkManager worldChunkManager,
            int chunkX,
            int chunkZ,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        if (!includeBiome && !includeBiomeTempRain) {
            return BiomeCapture.empty();
        }
        if (worldChunkManager == null) {
            return BiomeCapture.empty();
        }

        net.minecraft.server.BiomeBase[] biomeBase = worldChunkManager.getBiomeData(chunkX << 4, chunkZ << 4, 16, 16);

        net.minecraft.server.BiomeBase[] biomes = null;
        if (includeBiome) {
            biomes = new net.minecraft.server.BiomeBase[256];
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

    private void invokeChunkDataCopy(Object chunk, byte[] chunkBuffer) {
        if (chunk == null) {
            return;
        }
        try {
            chunk.getClass()
                    .getMethod("getData", byte[].class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .invoke(chunk, chunkBuffer, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(16), Integer.valueOf(128), Integer.valueOf(16), Integer.valueOf(0));
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private byte[] readHeightMap(Object chunk) {
        if (chunk == null) {
            return null;
        }
        try {
            Object value = chunk.getClass().getField("heightMap").get(chunk);
            return value instanceof byte[] ? (byte[]) value : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private net.minecraft.server.WorldChunkManager resolveWorldChunkManager(Object chunk) {
        if (chunk == null) {
            return null;
        }
        try {
            Object world = chunk.getClass().getField("world").get(chunk);
            Object manager = world.getClass().getMethod("getWorldChunkManager").invoke(world);
            return manager instanceof net.minecraft.server.WorldChunkManager ? (net.minecraft.server.WorldChunkManager) manager : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public static final class SnapshotData {
        private final byte[] chunkBuffer;
        private final byte[] heightMap;
        private final net.minecraft.server.BiomeBase[] biomes;
        private final double[] temperatures;
        private final double[] rainfall;

        public SnapshotData(byte[] chunkBuffer, byte[] heightMap, net.minecraft.server.BiomeBase[] biomes, double[] temperatures, double[] rainfall) {
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

        public net.minecraft.server.BiomeBase[] getBiomes() {
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
        private final net.minecraft.server.BiomeBase[] biomes;
        private final double[] temperatures;
        private final double[] rainfall;

        private BiomeCapture(net.minecraft.server.BiomeBase[] biomes, double[] temperatures, double[] rainfall) {
            this.biomes = biomes;
            this.temperatures = temperatures;
            this.rainfall = rainfall;
        }

        private static BiomeCapture empty() {
            return new BiomeCapture(null, null, null);
        }

        public net.minecraft.server.BiomeBase[] getBiomes() {
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
