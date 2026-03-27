package com.legacyminecraft.compat.bukkit;

public class Packet40EntityMetadata extends Packet {
    public final int entityId;
    public final Object data;

    public Packet40EntityMetadata(int entityId, Object data) {
        this.entityId = entityId;
        this.data = data;
    }
}
