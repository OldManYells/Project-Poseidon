package com.legacyminecraft.poseidon.runtime;


import java.util.Iterator;
import java.util.Set;

/**
 * Canonical whitelist command flow for legacy console wrappers.
 */
public final class ConsoleWhitelistCommandService {
    private static final ConsoleWhitelistCommandService INSTANCE = new ConsoleWhitelistCommandService();

    private ConsoleWhitelistCommandService() {
    }

    public static ConsoleWhitelistCommandService getInstance() {
        return INSTANCE;
    }

    public void handleWhitelistCommand(
            String sourceName,
            String fullCommand,
            ICommandListener listener,
            MinecraftServer server,
            PermissionGate permissionGate,
            ConsolePrinter consolePrinter
    ) {
        String[] arguments = fullCommand.split(" ");

        if (arguments.length < 2) {
            return;
        }

        String mode = arguments[1].toLowerCase();
        if ("on".equals(mode)) {
            if (!permissionGate.check(listener, "whitelist.enable")) {
                return;
            }
            consolePrinter.print(sourceName, "Turned on white-listing");
            server.propertyManager.b("white-list", true);
        } else if ("off".equals(mode)) {
            if (!permissionGate.check(listener, "whitelist.disable")) {
                return;
            }
            consolePrinter.print(sourceName, "Turned off white-listing");
            server.propertyManager.b("white-list", false);
        } else if ("list".equals(mode)) {
            if (!permissionGate.check(listener, "whitelist.list")) {
                return;
            }
            Set whiteList = server.serverConfigurationManager.e();
            String listedPlayers = "";

            for (Iterator iterator = whiteList.iterator(); iterator.hasNext(); listedPlayers = listedPlayers + (String) iterator.next() + " ") {
                ;
            }

            listener.sendMessage("White-listed players: " + listedPlayers);
        } else {
            if ("add".equals(mode) && arguments.length == 3) {
                if (!permissionGate.check(listener, "whitelist.add")) {
                    return;
                }
                String playerName = arguments[2].toLowerCase();
                server.serverConfigurationManager.k(playerName);
                consolePrinter.print(sourceName, "Added " + playerName + " to white-list");
            } else if ("remove".equals(mode) && arguments.length == 3) {
                if (!permissionGate.check(listener, "whitelist.remove")) {
                    return;
                }
                String playerName = arguments[2].toLowerCase();
                server.serverConfigurationManager.l(playerName);
                consolePrinter.print(sourceName, "Removed " + playerName + " from white-list");
            } else if ("reload".equals(mode)) {
                if (!permissionGate.check(listener, "whitelist.reload")) {
                    return;
                }
                server.serverConfigurationManager.f();
                consolePrinter.print(sourceName, "Reloaded white-list from file");
            }
        }
    }

    public interface PermissionGate {
        boolean check(ICommandListener listener, String permissionSuffix);
    }

    public interface ConsolePrinter {
        void print(String sourceName, String message);
    }
}
