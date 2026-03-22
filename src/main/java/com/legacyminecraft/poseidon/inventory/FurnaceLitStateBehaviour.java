package com.legacyminecraft.poseidon.inventory;

/**
 * Canonical furnace lit-state transition behaviour.
 */
public final class FurnaceLitStateBehaviour {
    private static final FurnaceLitStateBehaviour INSTANCE = new FurnaceLitStateBehaviour();

    private FurnaceLitStateBehaviour() {
    }

    public static FurnaceLitStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isBurning(int burnTime) {
        return burnTime > 0;
    }

    public boolean applyBurningStateTransition(boolean wasBurning,
                                               int burnTime,
                                               BurningStateApplier stateApplier) {
        boolean isBurningNow = isBurning(burnTime);
        if (wasBurning != isBurningNow) {
            stateApplier.apply(isBurningNow);
            return true;
        }

        return false;
    }

    public interface BurningStateApplier {
        void apply(boolean burning);
    }
}
