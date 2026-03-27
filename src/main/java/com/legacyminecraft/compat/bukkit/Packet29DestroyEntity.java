package com.legacyminecraft.compat.bukkit;

public class Packet29DestroyEntity extends Packet {
    public final int entityId;

    public Packet29DestroyEntity(int entityId) {
        this.entityId = entityId;
    }
}
