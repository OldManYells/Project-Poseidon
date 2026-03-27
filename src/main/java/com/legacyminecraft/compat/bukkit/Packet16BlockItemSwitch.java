package com.legacyminecraft.compat.bukkit;

public class Packet16BlockItemSwitch extends Packet {
    public final int itemInHandIndex;

    public Packet16BlockItemSwitch(int itemInHandIndex) {
        this.itemInHandIndex = itemInHandIndex;
    }
}
