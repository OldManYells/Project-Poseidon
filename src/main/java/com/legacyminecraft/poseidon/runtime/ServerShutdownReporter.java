package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.poseidon.PoseidonServer;
import com.legacyminecraft.poseidon.utility.PerformanceStatistic;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Canonical shutdown-time performance reporting.
 */
public final class ServerShutdownReporter {
    private ServerShutdownReporter() {
    }

    public static void logSessionStatistics(Logger log) {
        PoseidonServer poseidonServer = Poseidon.getServer();
        if (poseidonServer == null) {
            return;
        }

        if (!poseidonServer.getConfig().getConfigBoolean("settings.performance-monitoring.listener-reporting.print-statistics-on-shutdown.enabled")) {
            return;
        }

        logStatistics(log, "[Poseidon] Listener statistics from this session:", "Listener", poseidonServer.getSortedListenerPerformance());
        logStatistics(log, "[Poseidon] Synchronous task statistics from this session:", "Task", poseidonServer.getSortedTaskPerformance());
    }

    private static void logStatistics(Logger log, String header, String label, Map<String, PerformanceStatistic> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return;
        }

        log.info(header);
        for (Map.Entry<String, PerformanceStatistic> entry : statistics.entrySet()) {
            String name = entry.getKey();
            PerformanceStatistic stats = entry.getValue();
            if (stats == null || stats.getMaxExecutionTime() == 0) {
                continue;
            }

            log.info(String.format(
                    "[Poseidon] %s: %s - Processed %d events, Total Execution Time: %d ms, Avg Time: %d ms",
                    label,
                    name,
                    stats.getEventCount(),
                    stats.getTotalExecutionTime(),
                    stats.getAverageExecutionTime()));
        }
    }
}
