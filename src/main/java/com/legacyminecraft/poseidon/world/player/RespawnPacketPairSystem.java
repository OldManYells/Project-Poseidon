package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet9Respawn;
import net.minecraft.server.WorldServer;

/**
 * Canonical helpers for legacy dual-respawn packet sequencing during world transfer.
 */
public final class RespawnPacketPairSystem {
    private static final RespawnPacketPairSystem INSTANCE = new RespawnPacketPairSystem();

    private RespawnPacketPairSystem() {
    }

    public static RespawnPacketPairSystem getInstance() {
        return INSTANCE;
    }

    public void sendRespawnPacketPair(EntityPlayer player, WorldServer worldserver) {
        byte actualDimension = resolveActualDimension(worldserver);
        player.netServerHandler.sendPacket(new Packet9Respawn(resolveHandshakeDimension(actualDimension)));
        player.netServerHandler.sendPacket(new Packet9Respawn(actualDimension));
    }

    public byte resolveActualDimension(WorldServer worldserver) {
        return (byte) worldserver.getWorld().getEnvironment().getId();
    }

    public byte resolveHandshakeDimension(byte actualDimension) {
        return (byte) (actualDimension >= 0 ? -1 : 0);
    }
}
