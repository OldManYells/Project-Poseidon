package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Canonical execution flow for world-bootstrap progress and completion callbacks.
 */
public final class ServerWorldBootstrapProgressSystem {
    private static final ServerWorldBootstrapProgressSystem INSTANCE = new ServerWorldBootstrapProgressSystem();

    private ServerWorldBootstrapProgressSystem() {
    }

    public static ServerWorldBootstrapProgressSystem getInstance() {
        return INSTANCE;
    }

    public void applyProgress(String task, int percent, Logger logger, ProgressStateSink progressStateSink) {
        progressStateSink.apply(task, percent);
        logger.info(task + ": " + percent + "%");
    }

    public void completeBootstrap(CompletionActions completionActions) {
        completionActions.resetProgress();
        completionActions.enablePostWorldPlugins();
    }

    public interface ProgressStateSink {
        void apply(String task, int percent);
    }

    public interface CompletionActions {
        void resetProgress();

        void enablePostWorldPlugins();
    }
}
