package com.legacyminecraft.poseidon.world;


public final class ChunkWindowAccessBehaviour {
    private static final ChunkWindowAccessBehaviour INSTANCE = new ChunkWindowAccessBehaviour();

    private ChunkWindowAccessBehaviour() {
    }

    public static ChunkWindowAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public static final class WindowState {
        public final int minChunkX;
        public final int minChunkZ;
        public final Chunk[][] chunks;

        public WindowState(int minChunkX, int minChunkZ, Chunk[][] chunks) {
            this.minChunkX = minChunkX;
            this.minChunkZ = minChunkZ;
            this.chunks = chunks;
        }
    }

    public WindowState initialize(World world, int minX, int minZ, int maxX, int maxZ) {
        int minChunkX = minX >> 4;
        int minChunkZ = minZ >> 4;
        int maxChunkX = maxX >> 4;
        int maxChunkZ = maxZ >> 4;

        Chunk[][] chunks = new Chunk[maxChunkX - minChunkX + 1][maxChunkZ - minChunkZ + 1];

        for (int chunkX = minChunkX; chunkX <= maxChunkX; ++chunkX) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; ++chunkZ) {
                chunks[chunkX - minChunkX][chunkZ - minChunkZ] = world.getChunkAt(chunkX, chunkZ);
            }
        }

        return new WindowState(minChunkX, minChunkZ, chunks);
    }

    public int getTypeId(int minChunkX, int minChunkZ, Chunk[][] chunks, int blockX, int blockY, int blockZ) {
        if (blockY < 0 || blockY >= 128) {
            return 0;
        }

        int localChunkX = (blockX >> 4) - minChunkX;
        int localChunkZ = (blockZ >> 4) - minChunkZ;

        if (localChunkX >= 0 && localChunkX < chunks.length && localChunkZ >= 0 && localChunkZ < chunks[localChunkX].length) {
            Chunk chunk = chunks[localChunkX][localChunkZ];
            return chunk == null ? 0 : chunk.getTypeId(blockX & 15, blockY, blockZ & 15);
        }

        return 0;
    }

    public TileEntity getTileEntity(int minChunkX, int minChunkZ, Chunk[][] chunks, int blockX, int blockY, int blockZ) {
        int localChunkX = (blockX >> 4) - minChunkX;
        int localChunkZ = (blockZ >> 4) - minChunkZ;
        return chunks[localChunkX][localChunkZ].d(blockX & 15, blockY, blockZ & 15);
    }

    public int getData(int minChunkX, int minChunkZ, Chunk[][] chunks, int blockX, int blockY, int blockZ) {
        if (blockY < 0 || blockY >= 128) {
            return 0;
        }

        int localChunkX = (blockX >> 4) - minChunkX;
        int localChunkZ = (blockZ >> 4) - minChunkZ;

        return chunks[localChunkX][localChunkZ].getData(blockX & 15, blockY, blockZ & 15);
    }

    public Material getMaterial(int typeId) {
        return typeId == 0 ? Material.AIR : Block.byId[typeId].material;
    }

    public boolean isSolidRenderable(Block block) {
        return block != null && block.material.isSolid() && block.b();
    }
}
