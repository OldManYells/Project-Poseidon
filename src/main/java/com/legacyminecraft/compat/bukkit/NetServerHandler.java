package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat network-handler scaffold.
 */
public class NetServerHandler {
    public boolean disconnected;

    public boolean isReceivedKeepAlive() {
        return true;
    }

    public boolean isUsingReleaseToBeta() {
        return false;
    }

    public void sendPacket(Object packet) {
    }

    public void sendPacket(Packet packet) {
    }

    public void disconnect(String message) {
        this.disconnected = true;
    }

    public void chat(String message) {
    }

    public void teleport(Object to) {
    }

    public void a(double x, double y, double z, float yaw, float pitch) {
    }

    public int b() {
        return 0;
    }

    public Object getPlayer() {
        return null;
    }

    public String getConnectionType() {
        return "";
    }

    public String getCanonicalConnectionType() {
        return "";
    }
}
