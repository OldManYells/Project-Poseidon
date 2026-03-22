package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical execution flow for server run-loop failure recovery paths.
 */
public final class ServerRunFailureRecoverySystem {
    private static final ServerRunFailureRecoverySystem INSTANCE = new ServerRunFailureRecoverySystem();

    private ServerRunFailureRecoverySystem() {
    }

    public static ServerRunFailureRecoverySystem getInstance() {
        return INSTANCE;
    }

    public void executeOnInitFailure(FailureActions failureActions) {
        failureActions.drainCommandsUntilStopped();
    }

    public void executeOnException(Throwable throwable, Logger logger, FailureActions failureActions) {
        throwable.printStackTrace();
        logger.log(Level.SEVERE, "Unexpected exception", throwable);
        failureActions.drainCommandsUntilStopped();
    }

    public interface FailureActions {
        void drainCommandsUntilStopped();
    }
}
