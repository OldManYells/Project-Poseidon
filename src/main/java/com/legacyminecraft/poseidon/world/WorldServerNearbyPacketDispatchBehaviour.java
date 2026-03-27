package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for world-server local-area packet dispatch.
 */
public final class WorldServerNearbyPacketDispatchBehaviour {
    private static final WorldServerNearbyPacketDispatchBehaviour INSTANCE = new WorldServerNearbyPacketDispatchBehaviour();

    private WorldServerNearbyPacketDispatchBehaviour() {
    }

    public static WorldServerNearbyPacketDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void sendPacketNearby(
            ServerConfigurationManager serverConfigurationManager,
            double x,
            double y,
            double z,
            double radius,
            int dimension,
            Packet packet
    ) {
        serverConfigurationManager.sendPacketNearby(x, y, z, radius, dimension, packet);
    }
}
