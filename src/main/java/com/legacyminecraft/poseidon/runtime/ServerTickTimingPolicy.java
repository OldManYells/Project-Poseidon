package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Canonical main-loop elapsed-time normalization for server ticks.
 */
public final class ServerTickTimingPolicy {
    private static final ServerTickTimingPolicy INSTANCE = new ServerTickTimingPolicy();

    private ServerTickTimingPolicy() {
    }

    public static ServerTickTimingPolicy getInstance() {
        return INSTANCE;
    }

    public long normalizeElapsedMillis(long elapsedMillis, Logger logger) {
        if (elapsedMillis > 2000L) {
            logger.warning("Can\'t keep up! Did the system time change, or is the server overloaded?");
            return 2000L;
        }
        if (elapsedMillis < 0L) {
            logger.warning("Time ran backwards! Did the system time change?");
            return 0L;
        }
        return elapsedMillis;
    }
}
