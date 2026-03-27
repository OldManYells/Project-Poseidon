package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for world block query bounds and chunk-local lookup.
 */
public final class WorldBlockQueryBehaviour {
    private static final WorldBlockQueryBehaviour INSTANCE = new WorldBlockQueryBehaviour();

    private WorldBlockQueryBehaviour() {
    }

    public static WorldBlockQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isWithinWorldBounds(int x, int z) {
        return x >= -32000000 && z >= -32000000 && x < 32000000 && z <= 32000000;
    }

    public boolean isWithinBuildHeight(int y) {
        return y >= 0 && y < 128;
    }

    public int getTypeId(World world, int x, int y, int z) {
        if (!this.isWithinWorldBounds(x, z) || !this.isWithinBuildHeight(y)) {
            return 0;
        }

        return world.getChunkAt(x >> 4, z >> 4).getTypeId(x & 15, y, z & 15);
    }

    public boolean isEmpty(World world, int x, int y, int z) {
        return this.getTypeId(world, x, y, z) == 0;
    }

    public Material getMaterial(World world, int x, int y, int z) {
        int typeId = this.getTypeId(world, x, y, z);
        return typeId == 0 ? Material.AIR : Block.byId[typeId].material;
    }

    public int getData(World world, int x, int y, int z) {
        if (!this.isWithinWorldBounds(x, z) || !this.isWithinBuildHeight(y)) {
            return 0;
        }

        return world.getChunkAt(x >> 4, z >> 4).getData(x & 15, y, z & 15);
    }

    public int getLightLevelClamped(World world, int x, int y, int z, int fallbackValue) {
        if (y < 0) {
            return 0;
        }

        int clampedY = Math.min(y, 127);
        return world.getChunkAt(x >> 4, z >> 4).c(x & 15, clampedY, z & 15, fallbackValue);
    }

    public boolean usesNeighborBrightness(int typeId) {
        return typeId == Block.STEP.id
                || typeId == Block.SOIL.id
                || typeId == Block.COBBLESTONE_STAIRS.id
                || typeId == Block.WOOD_STAIRS.id;
    }

    public int maxNeighborBrightness(int up, int east, int west, int south, int north) {
        int brightness = up;

        if (east > brightness) {
            brightness = east;
        }

        if (west > brightness) {
            brightness = west;
        }

        if (south > brightness) {
            brightness = south;
        }

        if (north > brightness) {
            brightness = north;
        }

        return brightness;
    }

    public int getLightLevel(World world, int x, int y, int z, int worldBorderFallbackLight, int chunkFallbackLight) {
        if (!this.isWithinWorldBounds(x, z)) {
            return worldBorderFallbackLight;
        }

        return this.getLightLevelClamped(world, x, y, z, chunkFallbackLight);
    }

    public boolean hasDirectSkyAccess(World world, int x, int y, int z) {
        if (!this.isWithinWorldBounds(x, z)) {
            return false;
        }

        if (y < 0) {
            return false;
        }

        if (y >= 128) {
            return true;
        }

        if (!world.chunkProvider.isChunkLoaded(x >> 4, z >> 4)) {
            return false;
        }

        Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
        return chunk.c(x & 15, y, z & 15);
    }

    public int getHighestBlockYAt(World world, int x, int z) {
        if (!this.isWithinWorldBounds(x, z)) {
            return 0;
        }

        if (!world.chunkProvider.isChunkLoaded(x >> 4, z >> 4)) {
            return 0;
        }

        Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
        return chunk.b(x & 15, z & 15);
    }

    public boolean isChunkCellLoaded(World world, int x, int y, int z) {
        return world.getChunkAt(x >> 4, z >> 4).c(x & 15, y, z & 15);
    }
}
