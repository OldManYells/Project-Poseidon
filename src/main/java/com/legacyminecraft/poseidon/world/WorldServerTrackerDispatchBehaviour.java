package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for world-server tracker packet dispatch.
 */
public final class WorldServerTrackerDispatchBehaviour {
    private static final WorldServerTrackerDispatchBehaviour INSTANCE = new WorldServerTrackerDispatchBehaviour();

    private WorldServerTrackerDispatchBehaviour() {
    }

    public static WorldServerTrackerDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void sendPacketToTrackedEntity(MinecraftServer server, int dimension, Entity entity, Packet packet) {
        server.getTracker(dimension).sendPacketToEntity(entity, packet);
    }
}
