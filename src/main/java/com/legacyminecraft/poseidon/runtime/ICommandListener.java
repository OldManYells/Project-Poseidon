package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local command listener contract.
 */
public interface ICommandListener {
    String getName();

    void sendMessage(String message);
}
