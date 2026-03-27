package com.legacyminecraft.compat.bukkit;


/**
 * Canonical system for CraftScheduler queue polling, sleep calculation, and task dispatch orchestration.
 */
public final class CraftSchedulerRunLoopSystem {
    private static final CraftSchedulerRunLoopSystem INSTANCE = new CraftSchedulerRunLoopSystem();

    private CraftSchedulerRunLoopSystem() {
    }

    public static CraftSchedulerRunLoopSystem getInstance() {
        return INSTANCE;
    }

    public void runLoop(SchedulerRunLoopAccess access) {
        while (true) {
            boolean stop = false;
            long firstTick = -1L;
            long currentTick = -1L;
            CraftTask first = null;

            do {
                synchronized (access.getSchedulerQueueMonitor()) {
                    first = null;
                    if (!access.isSchedulerQueueEmpty()) {
                        first = access.getFirstScheduledTask();
                        if (first != null) {
                            currentTick = access.getCurrentTick();
                            firstTick = access.getExecutionTick(first);

                            if (currentTick >= firstTick) {
                                access.removeScheduledTask(first);
                                access.processTask(first);
                                if (access.getPeriod(first) >= 0L) {
                                    access.updateExecution(first);
                                    access.enqueueScheduledTask(first);
                                }
                            } else {
                                stop = true;
                            }
                        } else {
                            stop = true;
                        }
                    } else {
                        stop = true;
                    }
                }
            } while (!stop);

            long sleepTime;
            if (first == null) {
                sleepTime = 60000L;
            } else {
                currentTick = access.getCurrentTick();
                sleepTime = (firstTick - currentTick) * 50L + 25L;
            }

            if (sleepTime < 50L) {
                sleepTime = 50L;
            } else if (sleepTime > 60000L) {
                sleepTime = 60000L;
            }

            synchronized (access.getSchedulerQueueMonitor()) {
                try {
                    access.getSchedulerQueueMonitor().wait(sleepTime);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public interface SchedulerRunLoopAccess {
        Object getSchedulerQueueMonitor();

        boolean isSchedulerQueueEmpty();

        CraftTask getFirstScheduledTask();

        long getCurrentTick();

        long getExecutionTick(CraftTask task);

        long getPeriod(CraftTask task);

        void processTask(CraftTask task);

        void removeScheduledTask(CraftTask task);

        void updateExecution(CraftTask task);

        void enqueueScheduledTask(CraftTask task);
    }
}
