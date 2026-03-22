package com.legacyminecraft.poseidon.block;

/**
 * Canonical neighbor-physics scan policy for legacy sponge wrappers.
 */
public final class SpongePhysicsBehaviour {
    private static final SpongePhysicsBehaviour INSTANCE = new SpongePhysicsBehaviour();

    private SpongePhysicsBehaviour() {
    }

    public static SpongePhysicsBehaviour getInstance() {
        return INSTANCE;
    }

    public int removalRadius() {
        return 2;
    }

    public boolean shouldUseOptimizedRemoval(boolean configEnabled) {
        return configEnabled;
    }

    public void applyLegacyRemoval(PhysicsWorld world, int centerX, int centerY, int centerZ, int radius) {
        for (int x = centerX - radius; x <= centerX + radius; ++x) {
            for (int y = centerY - radius; y <= centerY + radius; ++y) {
                for (int z = centerZ - radius; z <= centerZ + radius; ++z) {
                    world.applyPhysics(x, y, z, world.getTypeId(x, y, z));
                }
            }
        }
    }

    public void applyOptimizedRemoval(
            PhysicsWorld world,
            int centerX,
            int centerY,
            int centerZ,
            int radius,
            int minY,
            int maxY,
            int flowingWaterId,
            int stationaryWaterId
    ) {
        for (int x = centerX - radius; x <= centerX + radius; ++x) {
            for (int y = centerY - radius; y <= centerY + radius; ++y) {
                if (y > maxY || y < minY) {
                    continue;
                }

                for (int z = centerZ - radius; z <= centerZ + radius; ++z) {
                    int type = world.getTypeId(x, y, z);
                    if (type != flowingWaterId && type != stationaryWaterId) {
                        continue;
                    }

                    world.applyPhysics(x, y, z, type);
                }
            }
        }
    }

    public interface PhysicsWorld {
        int getTypeId(int x, int y, int z);

        void applyPhysics(int x, int y, int z, int typeId);
    }
}
