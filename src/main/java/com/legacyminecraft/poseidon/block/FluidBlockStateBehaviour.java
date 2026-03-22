package com.legacyminecraft.poseidon.block;

/**
 * Canonical flow, rendering, collision, and lava-mix policy for legacy fluid wrappers.
 */
public final class FluidBlockStateBehaviour {
    private static final FluidBlockStateBehaviour INSTANCE = new FluidBlockStateBehaviour();

    private FluidBlockStateBehaviour() {
    }

    public static FluidBlockStateBehaviour getInstance() {
        return INSTANCE;
    }

    public float normalizedFluidHeight(int data) {
        int level = data;
        if (level >= 8) {
            level = 0;
        }
        return (float) (level + 1) / 9.0F;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side != 0 && side != 1 ? textureId + 1 : textureId;
    }

    public int resolveFlowData(boolean sameMaterial, int data) {
        if (!sameMaterial) {
            return -1;
        }
        return normalizeFlowData(data);
    }

    public int resolveRawFlowData(boolean sameMaterial, int data) {
        return sameMaterial ? data : -1;
    }

    public int normalizeFlowData(int data) {
        int normalized = data;
        if (normalized >= 8) {
            normalized = 0;
        }
        return normalized;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isNormalCube() {
        return false;
    }

    public boolean canCollideCheck(int side, boolean includeSourceOnly) {
        return includeSourceOnly && side == 0;
    }

    public boolean shouldRenderSide(boolean sameMaterial, boolean isIce, int side, boolean superResult) {
        if (sameMaterial) {
            return false;
        }
        if (isIce) {
            return false;
        }
        return side == 1 || superResult;
    }

    public int resolveDroppedItemId() {
        return 0;
    }

    public int resolveDroppedCount() {
        return 0;
    }

    public FlowVector computeFlowVector(FlowQuery query, int x, int y, int z) {
        double flowX = 0.0D;
        double flowY = 0.0D;
        double flowZ = 0.0D;
        int centerLevel = query.flowDataAt(x, y, z);

        for (int side = 0; side < 4; ++side) {
            int nx = x;
            int nz = z;
            if (side == 0) {
                nx = x - 1;
            }
            if (side == 1) {
                nz = z - 1;
            }
            if (side == 2) {
                nx = x + 1;
            }
            if (side == 3) {
                nz = z + 1;
            }

            int neighborLevel = query.flowDataAt(nx, y, nz);
            if (neighborLevel < 0) {
                if (!query.isSolidMaterial(nx, y, nz)) {
                    int belowLevel = query.flowDataAt(nx, y - 1, nz);
                    if (belowLevel >= 0) {
                        int delta = belowLevel - (centerLevel - 8);
                        flowX += (double) ((nx - x) * delta);
                        flowY += 0.0D;
                        flowZ += (double) ((nz - z) * delta);
                    }
                }
            } else {
                int delta = neighborLevel - centerLevel;
                flowX += (double) ((nx - x) * delta);
                flowY += 0.0D;
                flowZ += (double) ((nz - z) * delta);
            }
        }

        if (query.blockDataAt(x, y, z) >= 8) {
            boolean edgeFlow = false;
            if (edgeFlow || query.canFlowOutside(x, y, z - 1, 2)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x, y, z + 1, 3)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x - 1, y, z, 4)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x + 1, y, z, 5)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x, y + 1, z - 1, 2)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x, y + 1, z + 1, 3)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x - 1, y + 1, z, 4)) {
                edgeFlow = true;
            }
            if (edgeFlow || query.canFlowOutside(x + 1, y + 1, z, 5)) {
                edgeFlow = true;
            }

            if (edgeFlow) {
                FlowVector normalized = normalize(flowX, flowY, flowZ);
                flowX = normalized.x;
                flowY = normalized.y - 6.0D;
                flowZ = normalized.z;
            }
        }

        return normalize(flowX, flowY, flowZ);
    }

    public int resolveTickDelay(boolean isWater, boolean isLava) {
        return isWater ? 5 : (isLava ? 30 : 0);
    }

    public boolean shouldProcessLavaMix(int blockTypeId, int selfBlockId, boolean isLavaMaterial) {
        return blockTypeId == selfBlockId && isLavaMaterial;
    }

    public boolean hasWaterNeighbor(MaterialQuery query, int x, int y, int z) {
        return query.isWater(x, y, z - 1)
                || query.isWater(x, y, z + 1)
                || query.isWater(x - 1, y, z)
                || query.isWater(x + 1, y, z)
                || query.isWater(x, y + 1, z);
    }

    public int resolveLavaMixResult(int data, int obsidianBlockId, int cobblestoneBlockId) {
        if (data == 0) {
            return obsidianBlockId;
        }
        if (data <= 4) {
            return cobblestoneBlockId;
        }
        return -1;
    }

    public float resolveFizzPitch(float randomDelta) {
        return 2.6F + randomDelta * 0.8F;
    }

    public int resolveFizzSmokeCount() {
        return 8;
    }

    public FlowVector normalize(double x, double y, double z) {
        double length = Math.sqrt(x * x + y * y + z * z);
        if (length < 1.0E-4D) {
            return new FlowVector(0.0D, 0.0D, 0.0D);
        }
        return new FlowVector(x / length, y / length, z / length);
    }

    public interface FlowQuery {
        int flowDataAt(int x, int y, int z);

        boolean isSolidMaterial(int x, int y, int z);

        int blockDataAt(int x, int y, int z);

        boolean canFlowOutside(int x, int y, int z, int side);
    }

    public interface MaterialQuery {
        boolean isWater(int x, int y, int z);
    }

    public static final class FlowVector {
        public final double x;
        public final double y;
        public final double z;

        public FlowVector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
