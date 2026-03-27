package com.legacyminecraft.poseidon.network;

/**
 * Network-local packet1 login alias.
 */
public class Packet1Login extends com.legacyminecraft.compat.bukkit.Packet1Login {
    public Packet1Login() {
        super();
    }

    public Packet1Login(String name, int entityId, long seed, byte dimension) {
        super(name, entityId, seed, dimension);
    }
}

