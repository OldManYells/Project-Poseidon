package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.MathHelper;

import java.util.Random;

/**
 * Canonical roaming-target selection behaviour for creature AI wrappers.
 */
public final class EntityCreatureRoamingBehaviour {
    private static final EntityCreatureRoamingBehaviour INSTANCE = new EntityCreatureRoamingBehaviour();

    private EntityCreatureRoamingBehaviour() {
    }

    public static EntityCreatureRoamingBehaviour getInstance() {
        return INSTANCE;
    }

    public TargetCandidate selectBestRoamTarget(double locX, double locY, double locZ, Random random, CandidateScorer candidateScorer) {
        boolean foundCandidate = false;
        int bestX = -1;
        int bestY = -1;
        int bestZ = -1;
        float bestScore = -99999.0F;

        for (int attempt = 0; attempt < 10; ++attempt) {
            int candidateX = MathHelper.floor(locX + (double) random.nextInt(13) - 6.0D);
            int candidateY = MathHelper.floor(locY + (double) random.nextInt(7) - 3.0D);
            int candidateZ = MathHelper.floor(locZ + (double) random.nextInt(13) - 6.0D);
            float candidateScore = candidateScorer.score(candidateX, candidateY, candidateZ);

            if (candidateScore > bestScore) {
                bestScore = candidateScore;
                bestX = candidateX;
                bestY = candidateY;
                bestZ = candidateZ;
                foundCandidate = true;
            }
        }

        if (!foundCandidate) {
            return null;
        }

        return new TargetCandidate(bestX, bestY, bestZ);
    }

    public interface CandidateScorer {
        float score(int x, int y, int z);
    }

    public static final class TargetCandidate {
        private final int x;
        private final int y;
        private final int z;

        public TargetCandidate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
