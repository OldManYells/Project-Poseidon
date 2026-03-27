package com.legacyminecraft.compat.bukkit;

public class Packet3Chat extends Packet {
    public final String message;

    public Packet3Chat(String message) {
        this.message = message;
    }
}
