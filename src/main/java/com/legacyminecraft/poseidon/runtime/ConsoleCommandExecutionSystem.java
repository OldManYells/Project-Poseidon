package com.legacyminecraft.poseidon.runtime;

import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;

import java.util.logging.Logger;

/**
 * Role-aligned canonical facade for console command execution flow.
 */
public final class ConsoleCommandExecutionSystem {
    private static final ConsoleCommandExecutionSystem INSTANCE = new ConsoleCommandExecutionSystem();
    private final ConsoleCommandExecutionService delegate = ConsoleCommandExecutionService.getInstance();

    private ConsoleCommandExecutionSystem() {
    }

    public static ConsoleCommandExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean handleCommand(
            String command,
            ICommandListener commandListener,
            MinecraftServer server,
            final CommandSupport support,
            Logger logger
    ) {
        return delegate.handleCommand(command, commandListener, server, new ConsoleCommandExecutionService.CommandSupport() {
            @Override
            public boolean checkPermission(ICommandListener listener, String permissionSuffix) {
                return support.checkPermission(listener, permissionSuffix);
            }

            @Override
            public void print(String sourceName, String message) {
                support.print(sourceName, message);
            }

            @Override
            public void handleWhitelistCommand(String sourceName, String fullCommand, ICommandListener listener) {
                support.handleWhitelistCommand(sourceName, fullCommand, listener);
            }

            @Override
            public void sendHelp(ICommandListener listener) {
                support.sendHelp(listener);
            }

            @Override
            public int parseInt(String value, int fallback) {
                return support.parseInt(value, fallback);
            }
        }, logger);
    }

    public interface CommandSupport {
        boolean checkPermission(ICommandListener listener, String permissionSuffix);

        void print(String sourceName, String message);

        void handleWhitelistCommand(String sourceName, String fullCommand, ICommandListener listener);

        void sendHelp(ICommandListener listener);

        int parseInt(String value, int fallback);
    }
}
