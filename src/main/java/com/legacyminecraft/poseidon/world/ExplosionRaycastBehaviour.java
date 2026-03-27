package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.block.Block;

import java.util.Set;

/**
 * Canonical behaviour for explosion ray-march block collection.
 */
public final class ExplosionRaycastBehaviour {
    private static final ExplosionRaycastBehaviour INSTANCE = new ExplosionRaycastBehaviour();
    private static final int RAY_GRID = 16;
    private static final float RAY_STEP = 0.3F;

    private ExplosionRaycastBehaviour() {
    }

    public static ExplosionRaycastBehaviour getInstance() {
        return INSTANCE;
    }

    public void collectAffectedBlocks(Object world,
                                      Object source,
                                      float explosionSize,
                                      double explosionX,
                                      double explosionY,
                                      double explosionZ,
                                      Set<ChunkPosition> affectedBlocks) {
        java.util.Random random = readRandom(world);
        for (int sampleXIndex = 0; sampleXIndex < RAY_GRID; ++sampleXIndex) {
            for (int sampleYIndex = 0; sampleYIndex < RAY_GRID; ++sampleYIndex) {
                for (int sampleZIndex = 0; sampleZIndex < RAY_GRID; ++sampleZIndex) {
                    if (!isBoundaryRay(sampleXIndex, sampleYIndex, sampleZIndex)) {
                        continue;
                    }

                    double directionX = ((float) sampleXIndex / ((float) RAY_GRID - 1.0F) * 2.0F - 1.0F);
                    double directionY = ((float) sampleYIndex / ((float) RAY_GRID - 1.0F) * 2.0F - 1.0F);
                    double directionZ = ((float) sampleZIndex / ((float) RAY_GRID - 1.0F) * 2.0F - 1.0F);
                    double directionLength = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);

                    directionX /= directionLength;
                    directionY /= directionLength;
                    directionZ /= directionLength;

                    float rayStrength = explosionSize * (0.7F + random.nextFloat() * 0.6F);
                    double rayX = explosionX;
                    double rayY = explosionY;
                    double rayZ = explosionZ;

                    for (float step = RAY_STEP; rayStrength > 0.0F; rayStrength -= step * 0.75F) {
                        int blockX = MathHelper.floor(rayX);
                        int blockY = MathHelper.floor(rayY);
                        int blockZ = MathHelper.floor(rayZ);
                        int blockTypeId = invokeInt(world, "getTypeId", blockX, blockY, blockZ);

                        if (blockTypeId > 0) {
                            rayStrength -= (Block.byId[blockTypeId].a(asEntity(source)) + 0.3F) * step;
                        }

                        if (rayStrength > 0.0F) {
                            affectedBlocks.add(new ChunkPosition(blockX, blockY, blockZ));
                        }

                        rayX += directionX * (double) step;
                        rayY += directionY * (double) step;
                        rayZ += directionZ * (double) step;
                    }
                }
            }
        }
    }

    private boolean isBoundaryRay(int xIndex, int yIndex, int zIndex) {
        return xIndex == 0 || xIndex == RAY_GRID - 1
                || yIndex == 0 || yIndex == RAY_GRID - 1
                || zIndex == 0 || zIndex == RAY_GRID - 1;
    }

    private static java.util.Random readRandom(Object world) {
        try {
            java.lang.reflect.Field field = world.getClass().getField("random");
            Object value = field.get(world);
            if (value instanceof java.util.Random) {
                return (java.util.Random) value;
            }
        } catch (ReflectiveOperationException ignored) {
        }
        return new java.util.Random();
    }

    private static int invokeInt(Object target, String method, int x, int y, int z) {
        try {
            Object value = target.getClass()
                    .getMethod(method, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .invoke(target, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
            return value instanceof Integer ? ((Integer) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private static Entity asEntity(Object source) {
        return source instanceof Entity ? (Entity) source : null;
    }
}
