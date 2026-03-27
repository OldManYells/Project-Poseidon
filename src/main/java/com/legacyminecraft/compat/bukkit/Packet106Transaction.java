package com.legacyminecraft.compat.bukkit;

public class Packet106Transaction extends Packet {
    public final int windowId;
    public final int actionNumber;
    public final boolean accepted;

    public Packet106Transaction(int windowId, int actionNumber, boolean accepted) {
        this.windowId = windowId;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }
}
