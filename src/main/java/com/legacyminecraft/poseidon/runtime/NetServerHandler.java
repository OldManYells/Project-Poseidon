package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local net handler scaffold.
 */
public class NetServerHandler extends com.legacyminecraft.compat.bukkit.NetServerHandler {
    private final EntityPlayer player = new EntityPlayer();

    @Override
    public void sendPacket(com.legacyminecraft.compat.bukkit.Packet packet) {
    }

    public void sendPacket(Packet packet) {
    }

    public void disconnect(String reason) {
    }

    public void a(double x, double y, double z, float yaw, float pitch) {
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}
