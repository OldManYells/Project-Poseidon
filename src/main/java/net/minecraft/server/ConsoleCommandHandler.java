package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.ConsoleHelpMessageSystem;
import com.legacyminecraft.poseidon.runtime.ConsoleCommandExecutionSystem;
import com.legacyminecraft.poseidon.runtime.ConsoleCommandFeedbackSystem;
import com.legacyminecraft.poseidon.runtime.ConsolePermissionPolicy;
import com.legacyminecraft.poseidon.runtime.ConsoleWhitelistCommandSystem;

import java.util.logging.Logger;

// CraftBukkit start
// CraftBukkit end

public class ConsoleCommandHandler {

    private static Logger a = Logger.getLogger("Minecraft");
    private MinecraftServer server;
    private ICommandListener listener; // CraftBukkit
    private final ConsoleHelpMessageSystem consoleHelpMessageSystem = ConsoleHelpMessageSystem.getInstance();
    private final ConsoleCommandExecutionSystem consoleCommandExecutionSystem = ConsoleCommandExecutionSystem.getInstance();
    private final ConsoleCommandFeedbackSystem consoleCommandFeedbackSystem = ConsoleCommandFeedbackSystem.getInstance();
    private final ConsolePermissionPolicy consolePermissionPolicy = ConsolePermissionPolicy.getInstance();
    private final ConsoleWhitelistCommandSystem consoleWhitelistCommandSystem = ConsoleWhitelistCommandSystem.getInstance();
    private final ConsoleCommandExecutionSystem.CommandSupport commandSupport =
            new ConsoleCommandExecutionSystem.CommandSupport() {
                @Override
                public boolean checkPermission(ICommandListener listener, String permissionSuffix) {
                    return ConsoleCommandHandler.this.checkPermission(listener, permissionSuffix);
                }

                @Override
                public void print(String sourceName, String message) {
                    ConsoleCommandHandler.this.print(sourceName, message);
                }

                @Override
                public void handleWhitelistCommand(String sourceName, String fullCommand, ICommandListener listener) {
                    ConsoleCommandHandler.this.a(sourceName, fullCommand, listener);
                }

                @Override
                public void sendHelp(ICommandListener listener) {
                    ConsoleCommandHandler.this.a(listener);
                }

                @Override
                public int parseInt(String value, int fallback) {
                    return ConsoleCommandHandler.this.a(value, fallback);
                }
            };
    private final ConsoleWhitelistCommandSystem.PermissionGate whitelistPermissionGate =
            new ConsoleWhitelistCommandSystem.PermissionGate() {
                @Override
                public boolean check(ICommandListener listener, String permissionSuffix) {
                    return checkPermission(listener, permissionSuffix);
                }
            };
    private final ConsoleWhitelistCommandSystem.ConsolePrinter whitelistConsolePrinter =
            new ConsoleWhitelistCommandSystem.ConsolePrinter() {
                @Override
                public void print(String sourceName, String message) {
                    ConsoleCommandHandler.this.print(sourceName, message);
                }
            };

    public ConsoleCommandHandler(MinecraftServer minecraftserver) {
        this.server = minecraftserver;
    }

    private boolean checkPermission(ICommandListener listener, String command) {
        return consolePermissionPolicy.checkPermission(server, listener, command);
    }

    public boolean handle(ServerCommand servercommand) { // CraftBukkit - returns boolean
        final ICommandListener commandListener = servercommand.b;
        this.listener = commandListener; // CraftBukkit

        return consoleCommandExecutionSystem.handleCommand(
                servercommand.command,
                commandListener,
                this.server,
                this.commandSupport,
                a
        );
    }

    private void a(String s, String s1, ICommandListener icommandlistener) {
        this.listener = icommandlistener; // CraftBukkit
        consoleWhitelistCommandSystem.handleWhitelistCommand(
                s,
                s1,
                icommandlistener,
                this.server,
                this.whitelistPermissionGate,
                this.whitelistConsolePrinter
        );
    }

    private void a(ICommandListener icommandlistener) {
        consoleHelpMessageSystem.sendHelp(icommandlistener);
    }

    private void print(String s, String s1) {
        consoleCommandFeedbackSystem.print(this.listener, this.server, a, s, s1);
    }

    // CraftBukkit start
    private void informOps(String msg) {
        consoleCommandFeedbackSystem.informOps(this.listener, this.server, msg);
    }
    // CraftBukkit end

    private int a(String s, int i) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException numberformatexception) {
            return i;
        }
    }
}
