package com.legacyminecraft.compat.bukkit;

public class Packet39AttachEntity extends Packet {
    public final Entity entity;
    public final Entity vehicle;

    public Packet39AttachEntity(Entity entity, Entity vehicle) {
        this.entity = entity;
        this.vehicle = vehicle;
    }
}
