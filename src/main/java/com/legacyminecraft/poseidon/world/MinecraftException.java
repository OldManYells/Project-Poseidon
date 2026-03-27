package com.legacyminecraft.poseidon.world;

/**
 * World-local runtime exception scaffold for session-lock failures.
 */
public class MinecraftException extends com.legacyminecraft.poseidon.entity.MinecraftException {
    public MinecraftException(String message) {
        super(message);
    }
}
