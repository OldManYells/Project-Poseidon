package com.legacyminecraft.poseidon.runtime.progress;

import java.util.logging.Logger;

public final class ConversionProgressBehaviour {
    private static final ConversionProgressBehaviour INSTANCE = new ConversionProgressBehaviour();

    private ConversionProgressBehaviour() {
    }

    public static ConversionProgressBehaviour getInstance() {
        return INSTANCE;
    }

    public long initializeTimestamp(long nowMillis) {
        return nowMillis;
    }

    public long maybeLogProgress(long lastUpdateMillis, long nowMillis, int percent, Logger logger) {
        if (nowMillis - lastUpdateMillis >= 1000L) {
            logger.info("Converting... " + percent + "%");
            return nowMillis;
        }

        return lastUpdateMillis;
    }
}
