package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.Explosion;
import net.minecraft.server.ChunkPosition;
import net.minecraft.server.Packet54PlayNoteBlock;
import net.minecraft.server.Packet60Explosion;
import net.minecraft.server.Packet71Weather;
import net.minecraft.server.Packet38EntityStatus;

import java.util.Set;

/**
 * Canonical behaviour for world-server packet broadcast policies and packet construction.
 */
public final class WorldServerPacketBroadcastBehaviour {
    private static final WorldServerPacketBroadcastBehaviour INSTANCE = new WorldServerPacketBroadcastBehaviour();

    private WorldServerPacketBroadcastBehaviour() {
    }

    public static WorldServerPacketBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public double lightningBroadcastRadius() {
        return 512.0D;
    }

    public double localEffectBroadcastRadius() {
        return 64.0D;
    }

    public Packet71Weather createLightningPacket(Entity lightningEntity) {
        return new Packet71Weather(lightningEntity);
    }

    public boolean shouldBroadcastExplosion(Explosion explosion) {
        return !explosion.wasCanceled;
    }

    public Packet60Explosion createExplosionPacket(double x, double y, double z, float power, Set<ChunkPosition> affectedBlocks) {
        return new Packet60Explosion(x, y, z, power, affectedBlocks);
    }

    public Packet54PlayNoteBlock createPlayNotePacket(int x, int y, int z, int instrument, int note) {
        return new Packet54PlayNoteBlock(x, y, z, instrument, note);
    }

    public Packet38EntityStatus createEntityStatusPacket(Entity entity, byte status) {
        return new Packet38EntityStatus(entity.id, status);
    }
}
