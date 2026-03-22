package com.legacyminecraft.poseidon.inventory;

/**
 * Canonical furnace tick progression behaviour for elapsed-tick, cook-time, and burn-time transitions.
 */
public final class FurnaceTickProgressionBehaviour {
    private static final FurnaceTickProgressionBehaviour INSTANCE = new FurnaceTickProgressionBehaviour();

    private FurnaceTickProgressionBehaviour() {
    }

    public static FurnaceTickProgressionBehaviour getInstance() {
        return INSTANCE;
    }

    public int getCurrentTick() {
        return (int) (System.currentTimeMillis() / 50L);
    }

    public TickDelta computeTickDelta(int previousTick, int currentTick) {
        return new TickDelta(currentTick - previousTick, currentTick);
    }

    public CookProgress advanceCookProgress(boolean isBurning,
                                            boolean canBurn,
                                            int currentCookTime,
                                            int elapsedTicks,
                                            int cookCycleTicks) {
        if (isBurning && canBurn) {
            int nextCookTime = currentCookTime + elapsedTicks;
            if (nextCookTime >= cookCycleTicks) {
                return new CookProgress(nextCookTime % cookCycleTicks, true);
            }

            return new CookProgress(nextCookTime, false);
        }

        return new CookProgress(0, false);
    }

    public int decreaseBurnTime(int currentBurnTime, int elapsedTicks) {
        if (currentBurnTime > 0) {
            return currentBurnTime - elapsedTicks;
        }

        return currentBurnTime;
    }

    public static final class TickDelta {
        private final int elapsedTicks;
        private final int updatedLastTick;

        public TickDelta(int elapsedTicks, int updatedLastTick) {
            this.elapsedTicks = elapsedTicks;
            this.updatedLastTick = updatedLastTick;
        }

        public int getElapsedTicks() {
            return elapsedTicks;
        }

        public int getUpdatedLastTick() {
            return updatedLastTick;
        }
    }

    public static final class CookProgress {
        private final int cookTime;
        private final boolean shouldBurnOutput;

        public CookProgress(int cookTime, boolean shouldBurnOutput) {
            this.cookTime = cookTime;
            this.shouldBurnOutput = shouldBurnOutput;
        }

        public int getCookTime() {
            return cookTime;
        }

        public boolean shouldBurnOutput() {
            return shouldBurnOutput;
        }
    }
}
