package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical activation, particle, and drop policy for legacy redstone-ore wrappers.
 */
public final class RedstoneOreStateBehaviour {
    private static final RedstoneOreStateBehaviour INSTANCE = new RedstoneOreStateBehaviour();

    private RedstoneOreStateBehaviour() {
    }

    public static RedstoneOreStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int updateDelayTicks() {
        return 30;
    }

    public int resolveDropItemId(int redstoneItemId) {
        return redstoneItemId;
    }

    public int resolveDropCount(Random random) {
        return 4 + random.nextInt(2);
    }

    public boolean shouldSwitchToGlowing(int currentBlockId, int redstoneOreBlockId) {
        return currentBlockId == redstoneOreBlockId;
    }

    public boolean shouldRevertToNormal(int currentBlockId, int glowingRedstoneOreBlockId) {
        return currentBlockId == glowingRedstoneOreBlockId;
    }

    public void emitActivationParticles(ParticleEmitter emitter, OcclusionQuery occlusionQuery, Random random, int x, int y, int z) {
        double offset = 0.0625D;

        for (int face = 0; face < 6; ++face) {
            double particleX = (double) ((float) x + random.nextFloat());
            double particleY = (double) ((float) y + random.nextFloat());
            double particleZ = (double) ((float) z + random.nextFloat());

            if (face == 0 && !occlusionQuery.isOccluding(x, y + 1, z)) {
                particleY = (double) (y + 1) + offset;
            }

            if (face == 1 && !occlusionQuery.isOccluding(x, y - 1, z)) {
                particleY = (double) y - offset;
            }

            if (face == 2 && !occlusionQuery.isOccluding(x, y, z + 1)) {
                particleZ = (double) (z + 1) + offset;
            }

            if (face == 3 && !occlusionQuery.isOccluding(x, y, z - 1)) {
                particleZ = (double) z - offset;
            }

            if (face == 4 && !occlusionQuery.isOccluding(x + 1, y, z)) {
                particleX = (double) (x + 1) + offset;
            }

            if (face == 5 && !occlusionQuery.isOccluding(x - 1, y, z)) {
                particleX = (double) x - offset;
            }

            if (particleX < (double) x
                    || particleX > (double) (x + 1)
                    || particleY < 0.0D
                    || particleY > (double) (y + 1)
                    || particleZ < (double) z
                    || particleZ > (double) (z + 1)) {
                emitter.emit("reddust", particleX, particleY, particleZ);
            }
        }
    }

    public interface OcclusionQuery {
        boolean isOccluding(int x, int y, int z);
    }

    public interface ParticleEmitter {
        void emit(String particle, double x, double y, double z);
    }
}
