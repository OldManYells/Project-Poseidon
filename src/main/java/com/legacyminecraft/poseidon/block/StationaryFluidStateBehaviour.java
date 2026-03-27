package com.legacyminecraft.poseidon.block;


import java.util.Random;

/**
 * Canonical flowing-transition and lava-ignition policy for legacy stationary-fluid wrappers.
 */
public final class StationaryFluidStateBehaviour {
    private static final StationaryFluidStateBehaviour INSTANCE = new StationaryFluidStateBehaviour();

    private StationaryFluidStateBehaviour() {
    }

    public static StationaryFluidStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveFlowingBlockId(int stationaryBlockId) {
        return stationaryBlockId - 1;
    }

    public boolean shouldConvertToFlowing(int currentTypeId, int stationaryBlockId) {
        return currentTypeId == stationaryBlockId;
    }

    public boolean shouldAttemptLavaIgnition(Material material) {
        return material == Material.LAVA;
    }

    public int resolveIgnitionAttempts(Random random) {
        return random.nextInt(3);
    }

    public int resolveHorizontalOffset(Random random) {
        return random.nextInt(3) - 1;
    }

    public boolean isAirBlock(int typeId) {
        return typeId == 0;
    }

    public boolean shouldStopAtSolid(boolean solidMaterial) {
        return solidMaterial;
    }

    public boolean hasBurnableNeighbor(BurnableQuery query, int x, int y, int z) {
        return query.isBurnable(x - 1, y, z)
                || query.isBurnable(x + 1, y, z)
                || query.isBurnable(x, y, z - 1)
                || query.isBurnable(x, y, z + 1)
                || query.isBurnable(x, y - 1, z)
                || query.isBurnable(x, y + 1, z);
    }

    public interface BurnableQuery {
        boolean isBurnable(int x, int y, int z);
    }
}
