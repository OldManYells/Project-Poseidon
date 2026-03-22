package com.legacyminecraft.poseidon.block;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Material;

import java.util.Random;

/**
 * Canonical hydration, texture, and trampling policy for legacy farmland wrappers.
 */
public final class FarmlandStateBehaviour {
    private static final FarmlandStateBehaviour INSTANCE = new FarmlandStateBehaviour();

    private FarmlandStateBehaviour() {
    }

    public static FarmlandStateBehaviour getInstance() {
        return INSTANCE;
    }

    public AxisAlignedBB resolveCollisionBox(int x, int y, int z) {
        return AxisAlignedBB.b((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1));
    }

    public int resolveTextureBySideAndMoisture(int side, int moistureData, int topTextureId, int sideTextureId) {
        return side == 1 && moistureData > 0 ? topTextureId - 1 : (side == 1 ? topTextureId : sideTextureId);
    }

    public boolean shouldProcessMoistureTick(Random random) {
        return random.nextInt(5) == 0;
    }

    public boolean shouldDecayWithoutWaterAndRain(boolean hasNearbyWater, boolean hasRainAbove) {
        return !hasNearbyWater && !hasRainAbove;
    }

    public int resolveNextMoisture(int currentMoisture) {
        return currentMoisture > 0 ? currentMoisture - 1 : 0;
    }

    public int hydratedMoisture() {
        return 7;
    }

    public boolean shouldTurnToDirtWhenDry(int moisture, boolean hasCropsAbove) {
        return moisture <= 0 && !hasCropsAbove;
    }

    public boolean shouldTrampleToDirt(Random random) {
        return random.nextInt(4) == 0;
    }

    public boolean shouldTurnToDirtForBlockAbove(boolean aboveMaterialBuildable) {
        return aboveMaterialBuildable;
    }

    public boolean hasCropsAbove(TypeQuery query, int x, int y, int z, int cropsBlockId) {
        for (int xPos = x; xPos <= x; ++xPos) {
            for (int zPos = z; zPos <= z; ++zPos) {
                if (query.getTypeId(xPos, y + 1, zPos) == cropsBlockId) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasNearbyWater(MaterialQuery query, int x, int y, int z) {
        for (int xPos = x - 4; xPos <= x + 4; ++xPos) {
            for (int yPos = y; yPos <= y + 1; ++yPos) {
                for (int zPos = z - 4; zPos <= z + 4; ++zPos) {
                    if (query.getMaterial(xPos, yPos, zPos) == Material.WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public interface TypeQuery {
        int getTypeId(int x, int y, int z);
    }

    public interface MaterialQuery {
        Material getMaterial(int x, int y, int z);
    }
}
