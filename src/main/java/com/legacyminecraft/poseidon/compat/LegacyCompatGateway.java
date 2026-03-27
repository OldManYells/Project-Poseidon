package com.legacyminecraft.poseidon.compat;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Bridge owned by Poseidon and implemented by legacy layers.
 */
public interface LegacyCompatGateway {
    Object createMovingTileEntityPiston(int movedBlockId, int movedBlockData, int facing, boolean extending, boolean renderHead);

    boolean isTileEntityPiston(Object tileEntity);

    Object blockById(int blockId);

    int[] pistonOffsetX();

    int[] pistonOffsetY();

    int[] pistonOffsetZ();

    Object createPlayerInteractEntityEvent(Object bukkitPlayer, Object bukkitEntity);

    Object createPlayerAnimationEvent(Object bukkitPlayer);

    Object createPlayerToggleSneakEvent(Object bukkitPlayer, boolean sneaking);

    Object createEntityTargetEvent(Object bukkitSource, Object bukkitTarget, String reasonName);

    boolean isEventCancelled(Object event);

    Object getEntityTargetEventTarget(Object event);

    Object createPaintingBreakByWorldEvent(Object bukkitPainting);

    Object createPaintingBreakByEntityEvent(Object bukkitPainting, Object bukkitAttacker);

    Object paintingItemSingleton();

    Object createItemStackFromItem(Object item);

    Object createEntityItem(Object world, double x, double y, double z, Object itemStack);

    void addEntityToWorld(Object world, Object entity);

    Object resolveArtByNameOrDefault(String motive);

    Object createEntityTrackerEntry(Object entity, int trackingDistance, int updateInterval, boolean moving);

    boolean isEntityKind(Object entity, String kind);

    Object createPlayerListEntry(int hash, long key, Object value, Object next);

    Object createPacket130UpdateSign(int x, int y, int z, String[] lines);

    Object createPacket61(int i, int j, int k, int l, int i1);

    Object createPacket70Bed(int weatherPacketType);

    Object enumMovingObjectTypeTile();

    Object enumMovingObjectTypeEntity();

    Object createVec3(double x, double y, double z);

    Object createChunkProviderServer(Object worldServer, Object chunkLoader, Object chunkProvider);

    Object createChunkProviderByWorldProvider(Object world, Object worldProvider, long seed);

    void writeCompressed(Object tag, OutputStream outputStream);

    Object readCompressed(InputStream inputStream);
}
