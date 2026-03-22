package com.legacyminecraft.poseidon.block;

import net.minecraft.server.Entity;

/**
 * Canonical frame-validation, stability, and geometry policy for legacy portal wrappers.
 */
public final class PortalFrameBehaviour {
    private static final PortalFrameBehaviour INSTANCE = new PortalFrameBehaviour();

    private PortalFrameBehaviour() {
    }

    public static PortalFrameBehaviour getInstance() {
        return INSTANCE;
    }

    public PortalAxis resolveCreationAxis(boolean obsidianWest, boolean obsidianEast, boolean obsidianNorth, boolean obsidianSouth) {
        int axisX = (obsidianWest || obsidianEast) ? 1 : 0;
        int axisZ = (obsidianNorth || obsidianSouth) ? 1 : 0;
        return new PortalAxis(axisX, axisZ, axisX != axisZ);
    }

    public int resolvePhysicsAxisX(boolean hasPortalWest, boolean hasPortalEast) {
        return hasPortalWest || hasPortalEast ? 1 : 0;
    }

    public int resolvePhysicsAxisZ(boolean hasPortalWest, boolean hasPortalEast) {
        return hasPortalWest || hasPortalEast ? 0 : 1;
    }

    public Bounds resolvePortalBounds(boolean hasPortalWest, boolean hasPortalEast) {
        float f;
        float f1;
        if (!hasPortalWest && !hasPortalEast) {
            f = 0.125F;
            f1 = 0.5F;
            return new Bounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
        } else {
            f = 0.5F;
            f1 = 0.125F;
            return new Bounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
        }
    }

    public boolean shouldShiftOriginToLowerLeft(int candidateTypeId) {
        return candidateTypeId == 0;
    }

    public boolean shouldInspectFrameCoordinate(int xOffset, int yOffset) {
        return (xOffset != -1 && xOffset != 2) || (yOffset != -1 && yOffset != 3);
    }

    public boolean isFrameBoundaryCoordinate(int xOffset, int yOffset) {
        return xOffset == -1 || xOffset == 2 || yOffset == -1 || yOffset == 3;
    }

    public boolean isValidFrameBoundaryBlock(int typeId, int obsidianBlockId) {
        return typeId == obsidianBlockId;
    }

    public boolean isValidPortalInteriorBlock(int typeId, int fireBlockId) {
        return typeId == 0 || typeId == fireBlockId;
    }

    public int findPortalBaseY(TypeIdQuery query, int x, int y, int z, int portalBlockId) {
        int baseY = y;
        while (query.getTypeId(x, baseY - 1, z) == portalBlockId) {
            --baseY;
        }
        return baseY;
    }

    public boolean hasValidPortalBase(int baseTypeId, int obsidianBlockId) {
        return baseTypeId == obsidianBlockId;
    }

    public int countVerticalPortalSpan(TypeIdQuery query, int x, int baseY, int z, int portalBlockId) {
        int height = 1;
        while (height < 4 && query.getTypeId(x, baseY + height, z) == portalBlockId) {
            ++height;
        }
        return height;
    }

    public boolean hasValidPortalCap(int spanHeight, int capTypeId, int obsidianBlockId) {
        return spanHeight == 3 && capTypeId == obsidianBlockId;
    }

    public boolean hasCrossAxisPortalConflict(boolean xNeighborPortal, boolean zNeighborPortal) {
        return xNeighborPortal && zNeighborPortal;
    }

    public boolean hasValidSideSupportPair(int positiveSideTypeId, int negativeSideTypeId, int obsidianBlockId, int portalBlockId) {
        return positiveSideTypeId == obsidianBlockId && negativeSideTypeId == portalBlockId
                || negativeSideTypeId == obsidianBlockId && positiveSideTypeId == portalBlockId;
    }

    public boolean shouldDropPortalForInvalidSideSupport(boolean hasValidSupportPair) {
        return !hasValidSupportPair;
    }

    public int noDropCount() {
        return 0;
    }

    public boolean shouldTriggerEntityPortal(Entity entity) {
        return entity.vehicle == null && entity.passenger == null;
    }

    public void forEachPortalFrameCoordinate(int originX, int originY, int originZ, int axisX, int axisZ, CoordinateConsumer consumer) {
        for (int xOffset = -1; xOffset <= 2; ++xOffset) {
            for (int yOffset = -1; yOffset <= 3; ++yOffset) {
                if (!shouldInspectFrameCoordinate(xOffset, yOffset)) {
                    continue;
                }
                if (!isFrameBoundaryCoordinate(xOffset, yOffset)) {
                    continue;
                }
                consumer.accept(originX + axisX * xOffset, originY + yOffset, originZ + axisZ * xOffset);
            }
        }
    }

    public void forEachPortalInteriorCoordinate(int originX, int originY, int originZ, int axisX, int axisZ, CoordinateConsumer consumer) {
        for (int xOffset = 0; xOffset < 2; ++xOffset) {
            for (int yOffset = 0; yOffset < 3; ++yOffset) {
                consumer.accept(originX + axisX * xOffset, originY + yOffset, originZ + axisZ * xOffset);
            }
        }
    }

    public interface TypeIdQuery {
        int getTypeId(int x, int y, int z);
    }

    public interface CoordinateConsumer {
        void accept(int x, int y, int z);
    }

    public static final class PortalAxis {
        public final int axisX;
        public final int axisZ;
        public final boolean valid;

        public PortalAxis(int axisX, int axisZ, boolean valid) {
            this.axisX = axisX;
            this.axisZ = axisZ;
            this.valid = valid;
        }
    }

    public static final class Bounds {
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }
    }
}
