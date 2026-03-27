package com.legacyminecraft.poseidon.runtime;


/**
 * Role-aligned canonical facade for console permission policy.
 */
public final class ConsolePermissionPolicy {
    private static final ConsolePermissionPolicy INSTANCE = new ConsolePermissionPolicy();
    private final ConsolePermissionService delegate = ConsolePermissionService.getInstance();

    private ConsolePermissionPolicy() {
    }

    public static ConsolePermissionPolicy getInstance() {
        return INSTANCE;
    }

    public boolean hasPermission(MinecraftServer server, ICommandListener listener, String permissionNode) {
        return delegate.hasPermission(server, listener, permissionNode);
    }

    public boolean checkPermission(MinecraftServer server, ICommandListener listener, String command) {
        return delegate.checkPermission(server, listener, command);
    }
}
