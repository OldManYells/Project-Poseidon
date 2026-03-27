package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for portal search/scoring math used by legacy portal travel wrappers.
 */
public final class PortalTravelSearchBehaviour {
    private static final PortalTravelSearchBehaviour INSTANCE = new PortalTravelSearchBehaviour();

    private PortalTravelSearchBehaviour() {
    }

    public static PortalTravelSearchBehaviour getInstance() {
        return INSTANCE;
    }

    public int defaultSearchRadius() {
        return 128;
    }

    public int defaultCreateSearchRadius() {
        return 16;
    }

    public int clampPortalBaseY(int y) {
        if (y < 70) {
            return 70;
        }

        if (y > 118) {
            return 118;
        }

        return y;
    }

    public int orientationAxisX(int orientationIndex) {
        int axisX = orientationIndex % 2;
        if (orientationIndex % 4 >= 2) {
            axisX = -axisX;
        }

        return axisX;
    }

    public int orientationAxisZ(int orientationIndex) {
        int axisX = orientationIndex % 2;
        int axisZ = 1 - axisX;
        if (orientationIndex % 4 >= 2) {
            axisZ = -axisZ;
        }

        return axisZ;
    }

    public int primaryAxisX(int orientationIndex) {
        return orientationIndex % 2;
    }

    public int primaryAxisZ(int orientationIndex) {
        return 1 - this.primaryAxisX(orientationIndex);
    }

    public double centeredCoordinate(int blockCoordinate) {
        return (double) blockCoordinate + 0.5D;
    }

    public double axisDistance(double centeredCoordinate, double entityCoordinate) {
        return centeredCoordinate - entityCoordinate;
    }

    public double squaredDistance(double deltaX, double deltaY, double deltaZ) {
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public boolean isBetterDistance(double bestDistanceSquared, double candidateDistanceSquared) {
        return bestDistanceSquared < 0.0D || candidateDistanceSquared < bestDistanceSquared;
    }

    public int descendToPortalBase(World world, int x, int y, int z) {
        while (world.getTypeId(x, y - 1, z) == Block.PORTAL.id) {
            --y;
        }

        return y;
    }

    public double adjustPortalCenterX(World world, int x, int y, int z, double centerX) {
        if (world.getTypeId(x - 1, y, z) == Block.PORTAL.id) {
            centerX -= 0.5D;
        }

        if (world.getTypeId(x + 1, y, z) == Block.PORTAL.id) {
            centerX += 0.5D;
        }

        return centerX;
    }

    public double adjustPortalCenterZ(World world, int x, int y, int z, double centerZ) {
        if (world.getTypeId(x, y, z - 1) == Block.PORTAL.id) {
            centerZ -= 0.5D;
        }

        if (world.getTypeId(x, y, z + 1) == Block.PORTAL.id) {
            centerZ += 0.5D;
        }

        return centerZ;
    }
}
