package com.legacyminecraft.poseidon.runtime;


/**
 * Role-aligned canonical facade for whitelist command orchestration.
 */
public final class ConsoleWhitelistCommandSystem {
    private static final ConsoleWhitelistCommandSystem INSTANCE = new ConsoleWhitelistCommandSystem();
    private final ConsoleWhitelistCommandService delegate = ConsoleWhitelistCommandService.getInstance();

    private ConsoleWhitelistCommandSystem() {
    }

    public static ConsoleWhitelistCommandSystem getInstance() {
        return INSTANCE;
    }

    public void handleWhitelistCommand(
            String sourceName,
            String fullCommand,
            ICommandListener listener,
            MinecraftServer server,
            final PermissionGate permissionGate,
            final ConsolePrinter consolePrinter
    ) {
        delegate.handleWhitelistCommand(
                sourceName,
                fullCommand,
                listener,
                server,
                new ConsoleWhitelistCommandService.PermissionGate() {
                    @Override
                    public boolean check(ICommandListener listener, String permissionSuffix) {
                        return permissionGate.check(listener, permissionSuffix);
                    }
                },
                new ConsoleWhitelistCommandService.ConsolePrinter() {
                    @Override
                    public void print(String sourceName, String message) {
                        consolePrinter.print(sourceName, message);
                    }
                }
        );
    }

    public interface PermissionGate {
        boolean check(ICommandListener listener, String permissionSuffix);
    }

    public interface ConsolePrinter {
        void print(String sourceName, String message);
    }
}
