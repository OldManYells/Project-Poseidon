package com.legacyminecraft.poseidon.block;


/**
 * Canonical rail state, shape, and support policy for legacy minecart track wrappers.
 */
public final class RailStateBehaviour {
    private static final RailStateBehaviour INSTANCE = new RailStateBehaviour();

    private RailStateBehaviour() {
    }

    public static RailStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isRailBlockId(int blockId) {
        return blockId == Block.RAILS.id || blockId == Block.GOLDEN_RAIL.id || blockId == Block.DETECTOR_RAIL.id;
    }

    public float resolveCollisionHeight(int blockData) {
        return blockData >= 2 && blockData <= 5 ? 0.625F : 0.125F;
    }

    public int resolveTextureIndex(boolean poweredRailVariant, int blockId, int blockData, int textureId, int goldenRailBlockId) {
        if (poweredRailVariant) {
            if (blockId == goldenRailBlockId && (blockData & 8) == 0) {
                return textureId - 16;
            }
        } else if (blockData >= 6) {
            return textureId - 16;
        }

        return textureId;
    }

    public boolean canPlace(boolean hasSupportBelow) {
        return hasSupportBelow;
    }

    public int extractShape(int blockData, boolean poweredRailVariant) {
        return poweredRailVariant ? blockData & 7 : blockData;
    }

    public boolean shouldDropForMissingSupport(
            boolean hasSupportBelow,
            int shape,
            boolean hasEastSupport,
            boolean hasWestSupport,
            boolean hasNorthSupport,
            boolean hasSouthSupport
    ) {
        if (!hasSupportBelow) {
            return true;
        }

        if (shape == 2 && !hasEastSupport) {
            return true;
        }

        if (shape == 3 && !hasWestSupport) {
            return true;
        }

        if (shape == 4 && !hasNorthSupport) {
            return true;
        }

        return shape == 5 && !hasSouthSupport;
    }

    public boolean shouldNotifyBlockAboveOnPoweredStateChange(int shape) {
        return shape == 2 || shape == 3 || shape == 4 || shape == 5;
    }

    public int setPoweredState(int shape, boolean powered) {
        return powered ? shape | 8 : shape;
    }

    public boolean isPowered(int blockData) {
        return (blockData & 8) != 0;
    }

    public boolean canContinuePropagation(int depth) {
        return depth < 8;
    }

    public PropagationStep computePropagationStep(int x, int y, int z, int shape, boolean forward) {
        boolean checkBelowFallback = true;
        int expectedAxis = shape;
        int nextX = x;
        int nextY = y;
        int nextZ = z;

        switch (shape) {
            case 0:
                if (forward) {
                    ++nextZ;
                } else {
                    --nextZ;
                }
                expectedAxis = 0;
                break;
            case 1:
                if (forward) {
                    --nextX;
                } else {
                    ++nextX;
                }
                expectedAxis = 1;
                break;
            case 2:
                if (forward) {
                    --nextX;
                } else {
                    ++nextX;
                    ++nextY;
                    checkBelowFallback = false;
                }
                expectedAxis = 1;
                break;
            case 3:
                if (forward) {
                    --nextX;
                    ++nextY;
                    checkBelowFallback = false;
                } else {
                    ++nextX;
                }
                expectedAxis = 1;
                break;
            case 4:
                if (forward) {
                    ++nextZ;
                } else {
                    --nextZ;
                    ++nextY;
                    checkBelowFallback = false;
                }
                expectedAxis = 0;
                break;
            case 5:
                if (forward) {
                    ++nextZ;
                    ++nextY;
                    checkBelowFallback = false;
                } else {
                    --nextZ;
                }
                expectedAxis = 0;
                break;
            default:
                expectedAxis = shape;
        }

        return new PropagationStep(nextX, nextY, nextZ, checkBelowFallback, expectedAxis);
    }

    public boolean isPropagationShapeCompatible(int expectedAxis, int targetShape) {
        if (expectedAxis == 1 && (targetShape == 0 || targetShape == 4 || targetShape == 5)) {
            return false;
        }

        return expectedAxis != 0 || (targetShape != 1 && targetShape != 2 && targetShape != 3);
    }

    public static final class PropagationStep {
        private final int nextX;
        private final int nextY;
        private final int nextZ;
        private final boolean checkBelowFallback;
        private final int expectedAxis;

        public PropagationStep(int nextX, int nextY, int nextZ, boolean checkBelowFallback, int expectedAxis) {
            this.nextX = nextX;
            this.nextY = nextY;
            this.nextZ = nextZ;
            this.checkBelowFallback = checkBelowFallback;
            this.expectedAxis = expectedAxis;
        }

        public int getNextX() {
            return nextX;
        }

        public int getNextY() {
            return nextY;
        }

        public int getNextZ() {
            return nextZ;
        }

        public boolean shouldCheckBelowFallback() {
            return checkBelowFallback;
        }

        public int getExpectedAxis() {
            return expectedAxis;
        }
    }
}
