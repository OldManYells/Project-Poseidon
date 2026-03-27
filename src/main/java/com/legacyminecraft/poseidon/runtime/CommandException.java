package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local command exception scaffold.
 */
public class CommandException extends RuntimeException {
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }
}
