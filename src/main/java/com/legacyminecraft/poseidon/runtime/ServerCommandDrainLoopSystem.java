package com.legacyminecraft.poseidon.runtime;

/**
 * Role-aligned canonical system for command-drain idle loops.
 */
public final class ServerCommandDrainLoopSystem {
    private static final ServerCommandDrainLoopSystem INSTANCE = new ServerCommandDrainLoopSystem();
    private final ServerCommandDrainLoopService delegate = ServerCommandDrainLoopService.getInstance();

    private ServerCommandDrainLoopSystem() {
    }

    public static ServerCommandDrainLoopSystem getInstance() {
        return INSTANCE;
    }

    public void drainUntilStopped(LoopControl loopControl, long sleepMillis) {
        delegate.drainUntilStopped(adapt(loopControl), sleepMillis);
    }

    public void drainUntilStopped(LoopControl loopControl, Sleeper sleeper, long sleepMillis) {
        delegate.drainUntilStopped(adapt(loopControl), adapt(sleeper), sleepMillis);
    }

    private static ServerCommandDrainLoopService.LoopControl adapt(final LoopControl loopControl) {
        return new ServerCommandDrainLoopService.LoopControl() {
            @Override
            public boolean isRunning() {
                return loopControl.isRunning();
            }

            @Override
            public void drainCommands() {
                loopControl.drainCommands();
            }

            @Override
            public void handleInterruptedSleep(InterruptedException interruptedException) {
                loopControl.handleInterruptedSleep(interruptedException);
            }
        };
    }

    private static ServerCommandDrainLoopService.Sleeper adapt(final Sleeper sleeper) {
        return new ServerCommandDrainLoopService.Sleeper() {
            @Override
            public void sleep(long millis) throws InterruptedException {
                sleeper.sleep(millis);
            }
        };
    }

    public interface LoopControl {
        boolean isRunning();

        void drainCommands();

        void handleInterruptedSleep(InterruptedException interruptedException);
    }

    public interface Sleeper {
        void sleep(long millis) throws InterruptedException;
    }
}
