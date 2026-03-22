package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical helper for command-drain idle loops used during startup failure/exception handling.
 */
public final class ServerCommandDrainLoopService {
    private static final ServerCommandDrainLoopService INSTANCE = new ServerCommandDrainLoopService();

    private ServerCommandDrainLoopService() {
    }

    public static ServerCommandDrainLoopService getInstance() {
        return INSTANCE;
    }

    public void drainUntilStopped(LoopControl loopControl, long sleepMillis) {
        drainUntilStopped(loopControl, new ThreadSleeper(), sleepMillis);
    }

    public void drainUntilStopped(LoopControl loopControl, Sleeper sleeper, long sleepMillis) {
        while (loopControl.isRunning()) {
            loopControl.drainCommands();
            try {
                sleeper.sleep(sleepMillis);
            } catch (InterruptedException interruptedException) {
                loopControl.handleInterruptedSleep(interruptedException);
            }
        }
    }

    public interface LoopControl {
        boolean isRunning();

        void drainCommands();

        void handleInterruptedSleep(InterruptedException interruptedException);
    }

    public interface Sleeper {
        void sleep(long millis) throws InterruptedException;
    }

    private static final class ThreadSleeper implements Sleeper {
        @Override
        public void sleep(long millis) throws InterruptedException {
            Thread.sleep(millis);
        }
    }
}
