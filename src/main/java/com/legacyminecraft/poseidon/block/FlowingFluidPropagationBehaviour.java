package com.legacyminecraft.poseidon.block;

/**
 * Canonical level propagation and dispersion routing policy for legacy flowing-fluid wrappers.
 */
public final class FlowingFluidPropagationBehaviour {
    private static final FlowingFluidPropagationBehaviour INSTANCE = new FlowingFluidPropagationBehaviour();

    private FlowingFluidPropagationBehaviour() {
    }

    public static FlowingFluidPropagationBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveFlowIncreaseStep(boolean isLava, boolean isNether) {
        return isLava && !isNether ? 2 : 1;
    }

    public void convertToStillBlock(ConvertToStillSink sink, int x, int y, int z, int flowingBlockId, int data) {
        sink.setRawTypeIdAndData(x, y, z, flowingBlockId + 1, data);
        sink.markNeighborsDirty(x, y, z, x, y, z);
        sink.notifyBlock(x, y, z);
    }

    public LevelComputation computeNextLevel(LevelUpdateQuery query, int x, int y, int z, int currentLevel, int increaseStep, boolean isWater) {
        int sourceCount = 0;
        int minimumNeighbor = -100;

        NeighborLevelUpdate west = accumulateNeighbor(query.fluidLevel(x - 1, y, z), minimumNeighbor);
        minimumNeighbor = west.minLevel;
        sourceCount += west.sourceCountIncrement;

        NeighborLevelUpdate east = accumulateNeighbor(query.fluidLevel(x + 1, y, z), minimumNeighbor);
        minimumNeighbor = east.minLevel;
        sourceCount += east.sourceCountIncrement;

        NeighborLevelUpdate north = accumulateNeighbor(query.fluidLevel(x, y, z - 1), minimumNeighbor);
        minimumNeighbor = north.minLevel;
        sourceCount += north.sourceCountIncrement;

        NeighborLevelUpdate south = accumulateNeighbor(query.fluidLevel(x, y, z + 1), minimumNeighbor);
        minimumNeighbor = south.minLevel;
        sourceCount += south.sourceCountIncrement;

        int nextLevel = minimumNeighbor + increaseStep;
        if (nextLevel >= 8 || minimumNeighbor < 0) {
            nextLevel = -1;
        }

        int aboveLevel = query.fluidLevel(x, y + 1, z);
        if (aboveLevel >= 0) {
            nextLevel = aboveLevel >= 8 ? aboveLevel : aboveLevel + 8;
        }

        if (sourceCount >= 2 && isWater) {
            if (query.belowIsBuildable(x, y - 1, z)) {
                nextLevel = 0;
            } else if (query.belowIsSameMaterial(x, y - 1, z) && query.currentData(x, y, z) == 0) {
                nextLevel = 0;
            }
        }

        return new LevelComputation(nextLevel, sourceCount);
    }

    public LavaSlowdownResult applyLavaSlowdown(boolean isLava, int currentLevel, int nextLevel, int randomNextInt4, boolean flowingLavaFixEnabled) {
        boolean shouldConvertToStill = true;
        int adjustedNextLevel = nextLevel;

        if (isLava && currentLevel < 8 && nextLevel < 8 && nextLevel > currentLevel && randomNextInt4 != 0) {
            if (!flowingLavaFixEnabled) {
                adjustedNextLevel = currentLevel;
            }
            shouldConvertToStill = false;
        }

        return new LavaSlowdownResult(adjustedNextLevel, shouldConvertToStill);
    }

    public int resolveDownwardFlowLevel(int currentLevel) {
        return currentLevel >= 8 ? currentLevel : currentLevel + 8;
    }

    public int resolveSideFlowLevel(int currentLevel, int increaseStep) {
        int sideLevel = currentLevel + increaseStep;
        if (currentLevel >= 8) {
            sideLevel = 1;
        }
        return sideLevel >= 8 ? -1 : sideLevel;
    }

    public int computeSlopeDistance(SlopeQuery query, int x, int y, int z, int depth, int fromDirection) {
        int bestDistance = 1000;

        for (int direction = 0; direction < 4; ++direction) {
            if ((direction != 0 || fromDirection != 1)
                    && (direction != 1 || fromDirection != 0)
                    && (direction != 2 || fromDirection != 3)
                    && (direction != 3 || fromDirection != 2)) {
                int nx = x;
                int nz = z;

                if (direction == 0) {
                    nx = x - 1;
                }
                if (direction == 1) {
                    nx = x + 1;
                }
                if (direction == 2) {
                    nz = z - 1;
                }
                if (direction == 3) {
                    nz = z + 1;
                }

                if (!query.isBlocked(nx, y, nz) && !query.isSameMaterialLevelZero(nx, y, nz)) {
                    if (!query.isBlocked(nx, y - 1, nz)) {
                        return depth;
                    }

                    if (depth < 4) {
                        int candidate = computeSlopeDistance(query, nx, y, nz, depth + 1, direction);
                        if (candidate < bestDistance) {
                            bestDistance = candidate;
                        }
                    }
                }
            }
        }

        return bestDistance;
    }

    public boolean[] resolveOptimalFlowDirections(DirectionQuery query, int x, int y, int z, int[] costs, boolean[] selected) {
        for (int direction = 0; direction < 4; ++direction) {
            costs[direction] = 1000;
            int nx = x;
            int nz = z;

            if (direction == 0) {
                nx = x - 1;
            }
            if (direction == 1) {
                nx = x + 1;
            }
            if (direction == 2) {
                nz = z - 1;
            }
            if (direction == 3) {
                nz = z + 1;
            }

            if (!query.isBlocked(nx, y, nz) && !query.isSameMaterialLevelZero(nx, y, nz)) {
                if (!query.isBlocked(nx, y - 1, nz)) {
                    costs[direction] = 0;
                } else {
                    costs[direction] = computeSlopeDistance(query, nx, y, nz, 1, direction);
                }
            }
        }

        int minimum = costs[0];
        for (int direction = 1; direction < 4; ++direction) {
            if (costs[direction] < minimum) {
                minimum = costs[direction];
            }
        }

        for (int direction = 0; direction < 4; ++direction) {
            selected[direction] = costs[direction] == minimum;
        }

        return selected;
    }

    public boolean isBlockedType(int typeId, boolean materialSolid, int woodenDoorId, int ironDoorId, int signPostId, int ladderId, int sugarCaneId) {
        if (typeId != woodenDoorId && typeId != ironDoorId && typeId != signPostId && typeId != ladderId && typeId != sugarCaneId) {
            if (typeId == 0) {
                return false;
            }
            return materialSolid;
        }
        return true;
    }

    public boolean canFlowInto(boolean sameMaterial, boolean targetIsLava, boolean blocked) {
        return !sameMaterial && !targetIsLava && !blocked;
    }

    public NeighborLevelUpdate accumulateNeighbor(int rawNeighborLevel, int currentMin) {
        if (rawNeighborLevel < 0) {
            return new NeighborLevelUpdate(currentMin, 0);
        }

        int sourceIncrement = rawNeighborLevel == 0 ? 1 : 0;
        int normalized = rawNeighborLevel >= 8 ? 0 : rawNeighborLevel;
        int min = currentMin >= 0 && normalized >= currentMin ? currentMin : normalized;
        return new NeighborLevelUpdate(min, sourceIncrement);
    }

    public interface ConvertToStillSink {
        void setRawTypeIdAndData(int x, int y, int z, int typeId, int data);

        void markNeighborsDirty(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

        void notifyBlock(int x, int y, int z);
    }

    public interface LevelUpdateQuery {
        int fluidLevel(int x, int y, int z);

        boolean belowIsBuildable(int x, int y, int z);

        boolean belowIsSameMaterial(int x, int y, int z);

        int currentData(int x, int y, int z);
    }

    public interface SlopeQuery {
        boolean isBlocked(int x, int y, int z);

        boolean isSameMaterialLevelZero(int x, int y, int z);
    }

    public interface DirectionQuery extends SlopeQuery {
    }

    public static final class NeighborLevelUpdate {
        public final int minLevel;
        public final int sourceCountIncrement;

        public NeighborLevelUpdate(int minLevel, int sourceCountIncrement) {
            this.minLevel = minLevel;
            this.sourceCountIncrement = sourceCountIncrement;
        }
    }

    public static final class LevelComputation {
        public final int nextLevel;
        public final int sourceCount;

        public LevelComputation(int nextLevel, int sourceCount) {
            this.nextLevel = nextLevel;
            this.sourceCount = sourceCount;
        }
    }

    public static final class LavaSlowdownResult {
        public final int adjustedNextLevel;
        public final boolean shouldConvertToStill;

        public LavaSlowdownResult(int adjustedNextLevel, boolean shouldConvertToStill) {
            this.adjustedNextLevel = adjustedNextLevel;
            this.shouldConvertToStill = shouldConvertToStill;
        }
    }
}
