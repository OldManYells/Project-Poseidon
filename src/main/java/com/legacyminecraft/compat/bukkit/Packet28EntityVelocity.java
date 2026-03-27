package com.legacyminecraft.compat.bukkit;

public class Packet28EntityVelocity extends Packet {
    public final int entityId;
    public final double motX;
    public final double motY;
    public final double motZ;

    public Packet28EntityVelocity(int entityId, double motX, double motY, double motZ) {
        this.entityId = entityId;
        this.motX = motX;
        this.motY = motY;
        this.motZ = motZ;
    }
}
