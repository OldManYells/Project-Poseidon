package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local chat packet alias.
 */
public class Packet3Chat extends com.legacyminecraft.compat.bukkit.Packet3Chat {
    public Packet3Chat(String message) {
        super(message);
    }
}
