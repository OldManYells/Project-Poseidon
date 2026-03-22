package com.legacyminecraft.poseidon.compat.bukkit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behavior for CraftBukkit logger output stream flush/log rules.
 */
public final class LoggerOutputFlushBehaviour {
    private static final LoggerOutputFlushBehaviour INSTANCE = new LoggerOutputFlushBehaviour();

    private LoggerOutputFlushBehaviour() {
    }

    public static LoggerOutputFlushBehaviour getInstance() {
        return INSTANCE;
    }

    public void flush(ByteArrayOutputStream outputStream, String lineSeparator, Logger logger, Level level) throws IOException {
        synchronized (outputStream) {
            String record = outputStream.toString();
            outputStream.reset();

            if (shouldLog(record, lineSeparator)) {
                logger.logp(level, "", "", record);
            }
        }
    }

    public boolean shouldLog(String record, String lineSeparator) {
        return (record.length() > 0) && (!record.equals(lineSeparator));
    }
}
