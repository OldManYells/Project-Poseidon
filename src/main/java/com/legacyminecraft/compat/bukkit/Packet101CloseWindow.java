package com.legacyminecraft.compat.bukkit;

public class Packet101CloseWindow extends Packet {
    public final int windowId;

    public Packet101CloseWindow(int windowId) {
        this.windowId = windowId;
    }
}
