package com.legacyminecraft.poseidon.runtime;

import net.minecraft.server.ICommandListener;

/**
 * Role-aligned canonical facade for console help-message rendering.
 */
public final class ConsoleHelpMessageSystem {
    private static final ConsoleHelpMessageSystem INSTANCE = new ConsoleHelpMessageSystem();
    private final ConsoleHelpMessageService delegate = ConsoleHelpMessageService.getInstance();

    private ConsoleHelpMessageSystem() {
    }

    public static ConsoleHelpMessageSystem getInstance() {
        return INSTANCE;
    }

    public void sendHelp(ICommandListener listener) {
        delegate.sendHelp(listener);
    }
}
