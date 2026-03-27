package com.legacyminecraft.compat.bukkit;

public class Packet103SetSlot extends Packet {
    public final int windowId;
    public final int slot;
    public final ItemStack itemStack;

    public Packet103SetSlot(int windowId, int slot, ItemStack itemStack) {
        this.windowId = windowId;
        this.slot = slot;
        this.itemStack = itemStack;
    }
}
