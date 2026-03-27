package com.legacyminecraft.compat.bukkit;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Canonical compat network-manager scaffold.
 */
public class NetworkManager {
    public static int b;
    public static int c;
    private SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 25565);

    public void queue(Packet packet) {
    }

    public void d() {
    }

    public void a() {
    }

    public void a(String key, Object... args) {
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
