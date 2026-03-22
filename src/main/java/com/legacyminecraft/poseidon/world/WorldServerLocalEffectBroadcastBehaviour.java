package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.Explosion;
import net.minecraft.server.Packet;
import net.minecraft.server.ServerConfigurationManager;

/**
 * Canonical behaviour for world-server local-effect packet broadcast orchestration.
 */
public final class WorldServerLocalEffectBroadcastBehaviour {
    private static final WorldServerLocalEffectBroadcastBehaviour INSTANCE = new WorldServerLocalEffectBroadcastBehaviour();

    private WorldServerLocalEffectBroadcastBehaviour() {
    }

    public static WorldServerLocalEffectBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public void broadcastLightningEffect(
            ServerConfigurationManager serverConfigurationManager,
            WorldServerNearbyPacketDispatchBehaviour nearbyPacketDispatchBehaviour,
            WorldServerPacketBroadcastBehaviour packetBroadcastBehaviour,
            int dimension,
            Entity lightningEntity
    ) {
        nearbyPacketDispatchBehaviour.sendPacketNearby(
                serverConfigurationManager,
                lightningEntity.locX,
                lightningEntity.locY,
                lightningEntity.locZ,
                packetBroadcastBehaviour.lightningBroadcastRadius(),
                dimension,
                packetBroadcastBehaviour.createLightningPacket(lightningEntity)
        );
    }

    public void broadcastExplosionEffectIfNeeded(
            ServerConfigurationManager serverConfigurationManager,
            WorldServerNearbyPacketDispatchBehaviour nearbyPacketDispatchBehaviour,
            WorldServerPacketBroadcastBehaviour packetBroadcastBehaviour,
            int dimension,
            double x,
            double y,
            double z,
            float strength,
            Explosion explosion
    ) {
        if (!packetBroadcastBehaviour.shouldBroadcastExplosion(explosion)) {
            return;
        }

        Packet packet = packetBroadcastBehaviour.createExplosionPacket(x, y, z, strength, explosion.blocks);
        nearbyPacketDispatchBehaviour.sendPacketNearby(
                serverConfigurationManager,
                x,
                y,
                z,
                packetBroadcastBehaviour.localEffectBroadcastRadius(),
                dimension,
                packet
        );
    }

    public void broadcastNoteEffect(
            ServerConfigurationManager serverConfigurationManager,
            WorldServerNearbyPacketDispatchBehaviour nearbyPacketDispatchBehaviour,
            WorldServerPacketBroadcastBehaviour packetBroadcastBehaviour,
            int dimension,
            int x,
            int y,
            int z,
            int instrument,
            int pitch
    ) {
        nearbyPacketDispatchBehaviour.sendPacketNearby(
                serverConfigurationManager,
                (double) x,
                (double) y,
                (double) z,
                packetBroadcastBehaviour.localEffectBroadcastRadius(),
                dimension,
                packetBroadcastBehaviour.createPlayNotePacket(x, y, z, instrument, pitch)
        );
    }
}
