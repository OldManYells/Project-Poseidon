package com.legacyminecraft.compat.bukkit;

public class Packet100OpenWindow extends Packet {
    public final int windowId;
    public final int windowType;
    public final String title;
    public final int size;

    public Packet100OpenWindow(int windowId, int windowType, String title, int size) {
        this.windowId = windowId;
        this.windowType = windowType;
        this.title = title;
        this.size = size;
    }
}
