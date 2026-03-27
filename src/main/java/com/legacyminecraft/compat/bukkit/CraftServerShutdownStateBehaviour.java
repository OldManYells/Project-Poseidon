package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer shutdown-state wrapper glue.
 */
public final class CraftServerShutdownStateBehaviour {
    private static final CraftServerShutdownStateBehaviour INSTANCE = new CraftServerShutdownStateBehaviour();

    private CraftServerShutdownStateBehaviour() {
    }

    public static CraftServerShutdownStateBehaviour getInstance() {
        return INSTANCE;
    }

    public void shutdown(MinecraftServer console, ShutdownStateSink shutdownStateSink) {
        shutdownStateSink.setShuttingDown(true);
        console.a();
    }

    public boolean isShuttingdown(boolean shuttingDown) {
        return shuttingDown;
    }

    public void setShuttingdown(ShutdownStateSink shutdownStateSink, boolean shuttingDown) {
        shutdownStateSink.setShuttingDown(shuttingDown);
    }

    public interface ShutdownStateSink {
        void setShuttingDown(boolean shuttingDown);
    }
}
