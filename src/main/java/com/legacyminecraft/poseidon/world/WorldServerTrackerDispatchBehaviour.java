package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet;

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
