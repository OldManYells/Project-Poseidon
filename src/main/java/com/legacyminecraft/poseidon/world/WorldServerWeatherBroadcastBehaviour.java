package com.legacyminecraft.poseidon.world;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet70Bed;
import net.minecraft.server.WorldServer;

import java.util.List;

/**
 * Canonical behaviour for world-server weather-state packet fan-out.
 */
public final class WorldServerWeatherBroadcastBehaviour {
    private static final WorldServerWeatherBroadcastBehaviour INSTANCE = new WorldServerWeatherBroadcastBehaviour();

    private WorldServerWeatherBroadcastBehaviour() {
    }

    public static WorldServerWeatherBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public void broadcastWeatherStateChange(List players, WorldServer world, int weatherPacketType) {
        for (int playerIndex = 0; playerIndex < players.size(); ++playerIndex) {
            EntityPlayer entityPlayer = (EntityPlayer) players.get(playerIndex);

            if (entityPlayer.world == world) {
                entityPlayer.netServerHandler.sendPacket(new Packet70Bed(weatherPacketType));
            }
        }
    }
}
