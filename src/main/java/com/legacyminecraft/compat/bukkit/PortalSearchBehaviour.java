package com.legacyminecraft.compat.bukkit;

import net.minecraft.server.World;

/**
 * Canonical behavior for PortalTravelAgent nearest-portal search and center adjustment.
 */
public final class PortalSearchBehaviour {
    private static final PortalSearchBehaviour INSTANCE = new PortalSearchBehaviour();

    private PortalSearchBehaviour() {
    }

    public static PortalSearchBehaviour getInstance() {
        return INSTANCE;
    }

    public SearchResult findNearestPortal(
            World world,
            int centerBlockX,
            int centerBlockZ,
            double targetX,
            double targetY,
            double targetZ,
            int searchRadius,
            int portalBlockId
    ) {
        double bestDistanceSquared = -1.0D;
        int bestPortalX = 0;
        int bestPortalY = 0;
        int bestPortalZ = 0;

        for (int searchX = centerBlockX - searchRadius; searchX <= centerBlockX + searchRadius; ++searchX) {
            double deltaX = (double) searchX + 0.5D - targetX;

            for (int searchZ = centerBlockZ - searchRadius; searchZ <= centerBlockZ + searchRadius; ++searchZ) {
                double deltaZ = (double) searchZ + 0.5D - targetZ;

                for (int searchY = 127; searchY >= 0; --searchY) {
                    if (world.getTypeId(searchX, searchY, searchZ) != portalBlockId) {
                        continue;
                    }

                    while (world.getTypeId(searchX, searchY - 1, searchZ) == portalBlockId) {
                        --searchY;
                    }

                    double deltaY = (double) searchY + 0.5D - targetY;
                    double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
                    if (bestDistanceSquared < 0.0D || distanceSquared < bestDistanceSquared) {
                        bestDistanceSquared = distanceSquared;
                        bestPortalX = searchX;
                        bestPortalY = searchY;
                        bestPortalZ = searchZ;
                    }
                }
            }
        }

        if (bestDistanceSquared < 0.0D) {
            return SearchResult.notFound();
        }

        double portalCenterX = (double) bestPortalX + 0.5D;
        double portalCenterY = (double) bestPortalY + 0.5D;
        double portalCenterZ = (double) bestPortalZ + 0.5D;

        if (world.getTypeId(bestPortalX - 1, bestPortalY, bestPortalZ) == portalBlockId) {
            portalCenterX -= 0.5D;
        }

        if (world.getTypeId(bestPortalX + 1, bestPortalY, bestPortalZ) == portalBlockId) {
            portalCenterX += 0.5D;
        }

        if (world.getTypeId(bestPortalX, bestPortalY, bestPortalZ - 1) == portalBlockId) {
            portalCenterZ -= 0.5D;
        }

        if (world.getTypeId(bestPortalX, bestPortalY, bestPortalZ + 1) == portalBlockId) {
            portalCenterZ += 0.5D;
        }

        return SearchResult.found(portalCenterX, portalCenterY, portalCenterZ);
    }

    public static final class SearchResult {
        private final boolean found;
        private final double portalCenterX;
        private final double portalCenterY;
        private final double portalCenterZ;

        private SearchResult(boolean found, double portalCenterX, double portalCenterY, double portalCenterZ) {
            this.found = found;
            this.portalCenterX = portalCenterX;
            this.portalCenterY = portalCenterY;
            this.portalCenterZ = portalCenterZ;
        }

        public static SearchResult notFound() {
            return new SearchResult(false, 0.0D, 0.0D, 0.0D);
        }

        public static SearchResult found(double portalCenterX, double portalCenterY, double portalCenterZ) {
            return new SearchResult(true, portalCenterX, portalCenterY, portalCenterZ);
        }

        public boolean isFound() {
            return found;
        }

        public double getPortalCenterX() {
            return portalCenterX;
        }

        public double getPortalCenterY() {
            return portalCenterY;
        }

        public double getPortalCenterZ() {
            return portalCenterZ;
        }
    }
}
