package com.legacyminecraft.poseidon.world;

import java.lang.reflect.Field;
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

    public Packet71Weather createLightningPacket(Object lightningEntity) {
        return new Packet71Weather(null);
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

    public Packet38EntityStatus createEntityStatusPacket(Object entity, byte status) {
        return new Packet38EntityStatus(readInt(entity, "id"), status);
    }

    private static int readInt(Object target, String fieldName) {
        if (target == null) {
            return 0;
        }
        try {
            Field field = readField(target.getClass(), fieldName);
            return field.getInt(target);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
        }
    }

    private static Field readField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> currentType = type;
        while (currentType != null) {
            try {
                Field field = currentType.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
                currentType = currentType.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
