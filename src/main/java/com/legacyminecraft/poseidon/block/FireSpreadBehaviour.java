package com.legacyminecraft.poseidon.block;

/**
 * Canonical flammability and spread policy for legacy fire wrappers.
 */
public final class FireSpreadBehaviour {
    private static final FireSpreadBehaviour INSTANCE = new FireSpreadBehaviour();

    private FireSpreadBehaviour() {
    }

    public static FireSpreadBehaviour getInstance() {
        return INSTANCE;
    }

    public void setFlammability(int[] encouragement, int[] burnOdds, int blockId, int encouragementValue, int burnOddValue) {
        encouragement[blockId] = encouragementValue;
        burnOdds[blockId] = burnOddValue;
    }

    public int resolveDropCount() {
        return 0;
    }

    public int resolveTickRate() {
        return 40;
    }

    public boolean isEternalBase(int belowTypeId, int netherrackId) {
        return belowTypeId == netherrackId;
    }

    public boolean shouldRemoveForInvalidPlacement(boolean canPlace) {
        return !canPlace;
    }

    public boolean shouldRemoveForRain(boolean eternalBase, boolean worldRaining, boolean rainedOnSelfOrNeighbors) {
        return !eternalBase && worldRaining && rainedOnSelfOrNeighbors;
    }

    public int nextFireAge(int currentAge, int randomNextInt3) {
        if (currentAge < 15) {
            return currentAge + randomNextInt3 / 2;
        }
        return currentAge;
    }

    public boolean shouldRemoveWithoutSupport(boolean eternalBase, boolean hasBurnableNeighbors, boolean hasSolidBelow, int fireAge) {
        return !eternalBase && !hasBurnableNeighbors && (!hasSolidBelow || fireAge > 3);
    }

    public boolean shouldRemoveAtMaxAge(boolean eternalBase, boolean burnableBelow, int fireAge, int randomNextInt4) {
        return !eternalBase && !burnableBelow && fireAge == 15 && randomNextInt4 == 0;
    }

    public boolean shouldAttemptNeighborBurn(int randomRoll, int burnOdds) {
        return randomRoll < burnOdds;
    }

    public boolean shouldIgniteBurnedBlock(int randomRoll, boolean rainingAtTarget) {
        return randomRoll < 5 && !rainingAtTarget;
    }

    public int nextSpreadAge(int currentAge, int randomNextInt5) {
        int age = currentAge + randomNextInt5 / 4;
        return Math.min(age, 15);
    }

    public int resolveVerticalSpreadBound(int sourceY, int targetY, int baseBound) {
        int bound = baseBound;
        if (targetY > sourceY + 1) {
            bound += (targetY - (sourceY + 1)) * 100;
        }
        return bound;
    }

    public int resolveSpreadChanceFromNeighbor(int encouragement, int fireAge) {
        return (encouragement + 40) / (fireAge + 30);
    }

    public boolean shouldSpreadToAir(int spreadChance, int randomBoundRoll, boolean weatherBlockedAtTarget, boolean weatherBlockedAdjacent) {
        return spreadChance > 0 && randomBoundRoll <= spreadChance && !weatherBlockedAtTarget && !weatherBlockedAdjacent;
    }

    public boolean hasBurnableNeighbor(BurnableQuery query, int x, int y, int z) {
        return query.isBurnable(x + 1, y, z)
                || query.isBurnable(x - 1, y, z)
                || query.isBurnable(x, y - 1, z)
                || query.isBurnable(x, y + 1, z)
                || query.isBurnable(x, y, z - 1)
                || query.isBurnable(x, y, z + 1);
    }

    public int resolveNeighborEncouragement(MaxEncouragementQuery query, int x, int y, int z) {
        if (!query.isEmpty(x, y, z)) {
            return 0;
        }

        int max = 0;
        max = maxEncouragement(max, query.encouragementAt(x + 1, y, z));
        max = maxEncouragement(max, query.encouragementAt(x - 1, y, z));
        max = maxEncouragement(max, query.encouragementAt(x, y - 1, z));
        max = maxEncouragement(max, query.encouragementAt(x, y + 1, z));
        max = maxEncouragement(max, query.encouragementAt(x, y, z - 1));
        max = maxEncouragement(max, query.encouragementAt(x, y, z + 1));
        return max;
    }

    public int maxEncouragement(int current, int candidate) {
        return Math.max(current, candidate);
    }

    public boolean isBurnable(int encouragement) {
        return encouragement > 0;
    }

    public int resolveNeighborBurnOdds(int[] burnOdds, int typeId) {
        return burnOdds[typeId];
    }

    public int resolveEncouragement(int[] encouragement, int typeId) {
        return encouragement[typeId];
    }

    public boolean canPlace(boolean hasSolidBelow, boolean hasBurnableNeighbors) {
        return hasSolidBelow || hasBurnableNeighbors;
    }

    public boolean shouldDropOnPhysics(boolean hasSolidBelow, boolean hasBurnableNeighbors) {
        return !hasSolidBelow && !hasBurnableNeighbors;
    }

    public boolean shouldTryPortalCreation(int belowTypeId, int obsidianId) {
        return belowTypeId == obsidianId;
    }

    public interface BurnableQuery {
        boolean isBurnable(int x, int y, int z);
    }

    public interface MaxEncouragementQuery {
        boolean isEmpty(int x, int y, int z);

        int encouragementAt(int x, int y, int z);
    }
}
