package com.legacyminecraft.compat.bukkit;

public class Packet5EntityEquipment extends Packet {
    public final int entityId;
    public final int slot;
    public final ItemStack itemStack;

    public Packet5EntityEquipment(int entityId, int slot, ItemStack itemStack) {
        this.entityId = entityId;
        this.slot = slot;
        this.itemStack = itemStack;
    }
}
