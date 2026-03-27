package com.legacyminecraft.compat.bukkit;

public class Packet4UpdateTime extends Packet {
    public final long time;

    public Packet4UpdateTime(long time) {
        this.time = time;
    }
}
