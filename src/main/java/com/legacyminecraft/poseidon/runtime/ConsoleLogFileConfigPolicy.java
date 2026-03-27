package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical config-key policy for console log file routing.
 */
public final class ConsoleLogFileConfigPolicy {
    private static final ConsoleLogFileConfigPolicy INSTANCE = new ConsoleLogFileConfigPolicy();
    private static final String PER_DAY_LOG_FILE_ENABLED_KEY = "settings.per-day-log-file.enabled";
    private static final String LATEST_LOG_ENABLED_KEY = "settings.per-day-log-file.latest-log.enabled";

    private ConsoleLogFileConfigPolicy() {
    }

    public static ConsoleLogFileConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String perDayLogFileEnabledKey() {
        return PER_DAY_LOG_FILE_ENABLED_KEY;
    }

    public String latestLogEnabledKey() {
        return LATEST_LOG_ENABLED_KEY;
    }
}
