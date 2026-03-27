package com.legacyminecraft.compat.bukkit;

import java.util.List;

public class Packet104WindowItems extends Packet {
    public final int windowId;
    public final List<?> items;

    public Packet104WindowItems(int windowId, List<?> items) {
        this.windowId = windowId;
        this.items = items;
    }
}
