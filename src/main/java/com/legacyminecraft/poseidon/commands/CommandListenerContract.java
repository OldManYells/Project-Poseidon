package com.legacyminecraft.poseidon.commands;

/**
 * Canonical command listener contract bridged by legacy command wrappers.
 */
public interface CommandListenerContract {
    void sendMessage(String message);

    String getName();
}
