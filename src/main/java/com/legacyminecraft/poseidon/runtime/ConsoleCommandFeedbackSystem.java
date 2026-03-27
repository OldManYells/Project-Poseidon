package com.legacyminecraft.poseidon.runtime;


import java.util.logging.Logger;

/**
 * Role-aligned canonical facade for console feedback and op broadcast handling.
 */
public final class ConsoleCommandFeedbackSystem {
    private static final ConsoleCommandFeedbackSystem INSTANCE = new ConsoleCommandFeedbackSystem();
    private final ConsoleCommandFeedbackService delegate = ConsoleCommandFeedbackService.getInstance();

    private ConsoleCommandFeedbackSystem() {
    }

    public static ConsoleCommandFeedbackSystem getInstance() {
        return INSTANCE;
    }

    public void print(ICommandListener listener, MinecraftServer server, Logger logger, String sourceName, String message) {
        delegate.print(listener, server, logger, sourceName, message);
    }

    public void informOps(ICommandListener listener, MinecraftServer server, String message) {
        delegate.informOps(listener, server, message);
    }
}
