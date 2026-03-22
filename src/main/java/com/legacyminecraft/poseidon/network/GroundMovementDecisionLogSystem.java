package com.legacyminecraft.poseidon.network;

import java.util.logging.Logger;

/**
 * Canonical logger fan-out for ground-movement decisions.
 */
public final class GroundMovementDecisionLogSystem {
    private static final GroundMovementDecisionLogSystem INSTANCE = new GroundMovementDecisionLogSystem();

    private GroundMovementDecisionLogSystem() {
    }

    public static GroundMovementDecisionLogSystem getInstance() {
        return INSTANCE;
    }

    public void emitLogs(
            PlayerGroundMovementSystem.GroundMovementDecision decision,
            Logger warningLogger,
            ConsoleLogSink consoleLogSink
    ) {
        for (String warningLogLine : decision.getWarningLogs()) {
            warningLogger.warning(warningLogLine);
        }

        for (String consoleLogLine : decision.getConsoleLogs()) {
            consoleLogSink.println(consoleLogLine);
        }
    }

    public interface ConsoleLogSink {
        void println(String logLine);
    }
}
