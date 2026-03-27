package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local time update packet alias.
 */
public class Packet4UpdateTime extends com.legacyminecraft.compat.bukkit.Packet4UpdateTime {
    public Packet4UpdateTime(long worldTime) {
        super(worldTime);
    }
}
