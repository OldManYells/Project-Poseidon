package com.legacyminecraft.compat.bukkit;

public class Packet105CraftProgressBar extends Packet {
    public final int windowId;
    public final int progressBarId;
    public final int value;

    public Packet105CraftProgressBar(int windowId, int progressBarId, int value) {
        this.windowId = windowId;
        this.progressBarId = progressBarId;
        this.value = value;
    }
}
