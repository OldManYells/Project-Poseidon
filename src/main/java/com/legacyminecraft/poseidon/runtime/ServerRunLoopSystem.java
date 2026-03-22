package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Canonical orchestration for the server main run loop.
 */
public final class ServerRunLoopSystem {
    private static final ServerRunLoopSystem INSTANCE = new ServerRunLoopSystem();

    private ServerRunLoopSystem() {
    }

    public static ServerRunLoopSystem getInstance() {
        return INSTANCE;
    }

    public void executeLoop(boolean modLoaderSupport,
                            ServerTickTimingPolicy tickTimingPolicy,
                            ServerTickLoopPlanner tickLoopPlanner,
                            LoopHooks loopHooks,
                            Logger logger) throws InterruptedException {
        long previousTimeMillis = System.currentTimeMillis();
        long lagAccumulatorMillis = 0L;

        while (loopHooks.isServerRunning()) {
            Thread.sleep(1L);

            if (modLoaderSupport) {
                loopHooks.onModLoaderTick();
            }

            long nowMillis = System.currentTimeMillis();
            long elapsedMillis = tickTimingPolicy.normalizeElapsedMillis(nowMillis - previousTimeMillis, logger);
            lagAccumulatorMillis = tickLoopPlanner.accumulateLag(lagAccumulatorMillis, elapsedMillis);
            previousTimeMillis = nowMillis;

            ServerTickLoopPlanner.TickExecutionPlan tickExecutionPlan =
                    tickLoopPlanner.createTickExecutionPlan(lagAccumulatorMillis, loopHooks.isEveryoneDeeplySleeping());
            lagAccumulatorMillis = tickExecutionPlan.getNextLagAccumulatorMillis();

            for (int tickRun = 0; tickRun < tickExecutionPlan.getTicksToExecute(); ++tickRun) {
                if (tickExecutionPlan.shouldUpdateWatchdogPerTick()) {
                    loopHooks.updateWatchdog();
                }
                loopHooks.runMainTick();
            }
        }
    }

    public interface LoopHooks {
        boolean isServerRunning();

        boolean isEveryoneDeeplySleeping();

        void onModLoaderTick();

        void updateWatchdog();

        void runMainTick();
    }
}
