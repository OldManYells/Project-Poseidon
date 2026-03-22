package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical shutdown flow coordinator for Bukkit stop-command wrappers.
 */
public final class StopCommandExecutionService {
    private static final StopCommandExecutionService INSTANCE = new StopCommandExecutionService();
    private static final String START_MESSAGE = "Starting Server Shutdown, Saving Data.";
    private static final String FINAL_MESSAGE = "Stopping the server..";
    private static final long FINAL_STOP_DELAY_TICKS = 100L;

    private StopCommandExecutionService() {
    }

    public static StopCommandExecutionService getInstance() {
        return INSTANCE;
    }

    public void executeStopCommand(ShutdownActions actions, String kickMessage) {
        actions.broadcast(START_MESSAGE);
        actions.setShuttingDown(true);
        actions.saveAndKickPlayers(kickMessage);
        actions.saveWorlds();
        actions.scheduleFinalStop(new Runnable() {
            @Override
            public void run() {
                actions.broadcast(FINAL_MESSAGE);
                actions.shutdownNow();
            }
        }, FINAL_STOP_DELAY_TICKS);
    }

    public interface ShutdownActions {
        void broadcast(String message);

        void setShuttingDown(boolean shuttingDown);

        void saveAndKickPlayers(String kickMessage);

        void saveWorlds();

        void scheduleFinalStop(Runnable task, long delayTicks);

        void shutdownNow();
    }
}
