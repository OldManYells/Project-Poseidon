package com.legacyminecraft.poseidon.runtime;


/**
 * Canonical guard policy for command-dispatch preconditions and payload safety.
 */
public final class ServerCommandDispatchPolicy {
    private static final ServerCommandDispatchPolicy INSTANCE = new ServerCommandDispatchPolicy();

    private ServerCommandDispatchPolicy() {
    }

    public static ServerCommandDispatchPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isBukkitReady(Server server, ConsoleCommandSender consoleSender) {
        return server != null && consoleSender != null;
    }

    public String resolveDispatchCommand(String eventCommand, String originalCommand) {
        if (eventCommand != null) {
            return eventCommand;
        }
        return originalCommand == null ? "" : originalCommand;
    }

    public boolean shouldDispatch(String commandText) {
        return commandText != null && commandText.trim().length() > 0;
    }
}
