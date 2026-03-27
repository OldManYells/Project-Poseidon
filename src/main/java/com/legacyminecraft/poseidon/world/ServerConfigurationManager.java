package com.legacyminecraft.poseidon.world;

/**
 * World-local server configuration manager alias.
 */
public class ServerConfigurationManager extends com.legacyminecraft.poseidon.runtime.ServerConfigurationManager {
    public void sendPacketNearby(double x, double y, double z, double radius, int dimension, Packet packet) {
        super.sendPacketNearby(x, y, z, radius, dimension, packet);
    }
}
