package com.legacyminecraft.poseidon.runtime;

/**
 * Role-aligned canonical system for Bukkit stop-command shutdown orchestration.
 */
public final class StopCommandExecutionSystem {
    private static final StopCommandExecutionSystem INSTANCE = new StopCommandExecutionSystem();
    private final StopCommandExecutionService delegate = StopCommandExecutionService.getInstance();

    private StopCommandExecutionSystem() {
    }

    public static StopCommandExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void executeStopCommand(final ShutdownActions actions, String kickMessage) {
        delegate.executeStopCommand(new StopCommandExecutionService.ShutdownActions() {
            @Override
            public void broadcast(String message) {
                actions.broadcast(message);
            }

            @Override
            public void setShuttingDown(boolean shuttingDown) {
                actions.setShuttingDown(shuttingDown);
            }

            @Override
            public void saveAndKickPlayers(String kickMessage) {
                actions.saveAndKickPlayers(kickMessage);
            }

            @Override
            public void saveWorlds() {
                actions.saveWorlds();
            }

            @Override
            public void scheduleFinalStop(Runnable task, long delayTicks) {
                actions.scheduleFinalStop(task, delayTicks);
            }

            @Override
            public void shutdownNow() {
                actions.shutdownNow();
            }
        }, kickMessage);
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

