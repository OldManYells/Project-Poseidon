package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;

import java.util.List;

/**
 * Canonical behaviour for dispatching chunk-scoped packets to subscribed players.
 */
public final class PlayerChunkPacketDispatchBehaviour {
    private static final PlayerChunkPacketDispatchBehaviour INSTANCE = new PlayerChunkPacketDispatchBehaviour();

    private PlayerChunkPacketDispatchBehaviour() {
    }

    public static PlayerChunkPacketDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void sendToSubscribedPlayers(List playersInChunk, ChunkCoordIntPair chunkLocation, Packet packet) {
        for (int index = 0; index < playersInChunk.size(); ++index) {
            EntityPlayer player = (EntityPlayer) playersInChunk.get(index);
            if (player.playerChunkCoordIntPairs.contains(chunkLocation)) {
                player.netServerHandler.sendPacket(packet);
            }
        }
    }
}
