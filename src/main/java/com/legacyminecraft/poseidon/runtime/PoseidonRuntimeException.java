package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical unchecked exception base for Poseidon runtime failures.
 */
public class PoseidonRuntimeException extends RuntimeException {
    public PoseidonRuntimeException(String message) {
        super(message);
    }
}
