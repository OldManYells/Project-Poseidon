package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical planner for server run-loop lag accumulation and tick execution counts.
 */
public final class ServerTickLoopPlanner {
    private static final long TICK_INTERVAL_MILLIS = 50L;
    private static final ServerTickLoopPlanner INSTANCE = new ServerTickLoopPlanner();

    private ServerTickLoopPlanner() {
    }

    public static ServerTickLoopPlanner getInstance() {
        return INSTANCE;
    }

    public long accumulateLag(long lagAccumulatorMillis, long elapsedMillis) {
        return lagAccumulatorMillis + elapsedMillis;
    }

    public TickExecutionPlan createTickExecutionPlan(long lagAccumulatorMillis, boolean everyoneDeeplySleeping) {
        if (everyoneDeeplySleeping) {
            return new TickExecutionPlan(1, 0L, false);
        }

        int ticksToExecute = 0;
        long remainingLag = lagAccumulatorMillis;
        while (remainingLag > TICK_INTERVAL_MILLIS) {
            ++ticksToExecute;
            remainingLag -= TICK_INTERVAL_MILLIS;
        }
        return new TickExecutionPlan(ticksToExecute, remainingLag, true);
    }

    public int computeCurrentTick(long currentTimeMillis) {
        return (int) (currentTimeMillis / TICK_INTERVAL_MILLIS);
    }

    public static final class TickExecutionPlan {
        private final int ticksToExecute;
        private final long nextLagAccumulatorMillis;
        private final boolean shouldUpdateWatchdogPerTick;

        private TickExecutionPlan(int ticksToExecute, long nextLagAccumulatorMillis, boolean shouldUpdateWatchdogPerTick) {
            this.ticksToExecute = ticksToExecute;
            this.nextLagAccumulatorMillis = nextLagAccumulatorMillis;
            this.shouldUpdateWatchdogPerTick = shouldUpdateWatchdogPerTick;
        }

        public int getTicksToExecute() {
            return ticksToExecute;
        }

        public long getNextLagAccumulatorMillis() {
            return nextLagAccumulatorMillis;
        }

        public boolean shouldUpdateWatchdogPerTick() {
            return shouldUpdateWatchdogPerTick;
        }
    }
}
