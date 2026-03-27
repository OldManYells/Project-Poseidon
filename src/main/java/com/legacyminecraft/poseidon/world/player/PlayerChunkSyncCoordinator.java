package com.legacyminecraft.poseidon.world.player;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.compat.bukkit.ChunkCompressionDispatchBridge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Canonical player chunk/entity sync orchestration.
 */
public final class PlayerChunkSyncCoordinator {
    private static final PlayerChunkSyncCoordinator INSTANCE = new PlayerChunkSyncCoordinator();
    private final ChunkCompressionDispatchBridge chunkCompressionDispatchBridge = ChunkCompressionDispatchBridge.getInstance();

    private PlayerChunkSyncCoordinator() {
    }

    public static PlayerChunkSyncCoordinator getInstance() {
        return INSTANCE;
    }

    public void flushEntityRemovalQueue(EntityPlayer player) {
        while (!player.removeQueue.isEmpty()) {
            int batchSize = Math.min(player.removeQueue.size(), 127);
            int[] entityIds = new int[batchSize];
            Iterator iterator = player.removeQueue.iterator();
            int index = 0;

            while (iterator.hasNext() && index < batchSize) {
                entityIds[index++] = ((Integer) iterator.next()).intValue();
                iterator.remove();
            }

            for (int i = 0; i < entityIds.length; i++) {
                player.netServerHandler.sendPacket(new Packet29DestroyEntity(entityIds[i]));
            }
        }
    }

    public void sendMapUpdatePackets(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSize(); ++i) {
            ItemStack itemstack = player.inventory.getItem(i);

            if (itemstack != null && Item.byId[itemstack.id].b() && player.netServerHandler.b() <= 2) {
                Packet packet = ((ItemWorldMapBase) Item.byId[itemstack.id]).b(itemstack, player.world, player);
                if (packet != null) {
                    player.netServerHandler.sendPacket(packet);
                }
            }
        }
    }

    public void processChunkSendQueue(EntityPlayer player, boolean flushTick) {
        if (!flushTick || player.chunkCoordIntPairQueue.isEmpty()) {
            return;
        }

        if (PoseidonConfig.getInstance().getBoolean("settings.faster-packets.enabled", true)) {
            processFastChunkQueue(player);
            return;
        }

        processLegacyChunkQueue(player);
    }

    private void processFastChunkQueue(EntityPlayer player) {
        ArrayList chunksToSend = new ArrayList();
        Iterator iterator = player.chunkCoordIntPairQueue.iterator();
        ArrayList tileEntities = new ArrayList();

        while (iterator.hasNext() && chunksToSend.size() < 5) {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) iterator.next();

            iterator.remove();
            if (chunkcoordintpair != null && player.world.isLoaded(chunkcoordintpair.x << 4, 0, chunkcoordintpair.z << 4)) {
                Chunk chunk = player.world.getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
                chunksToSend.add(chunk);
                tileEntities.addAll(chunk.tileEntities.values());
            }
        }

        if (chunksToSend.isEmpty()) {
            return;
        }

        Iterator chunkIterator = chunksToSend.iterator();
        while (chunkIterator.hasNext()) {
            Chunk chunk = (Chunk) chunkIterator.next();
            player.netServerHandler.sendPacket(new Packet51MapChunk(chunk.x * 16, 0, chunk.z * 16, 16, 128, 16, player.getWorldServer()));
            player.getWorldServer().tracker.a(player, chunk);
        }

        Iterator tileIterator = tileEntities.iterator();
        while (tileIterator.hasNext()) {
            sendTileEntityUpdate(player, (TileEntity) tileIterator.next());
        }
    }

    private void processLegacyChunkQueue(EntityPlayer player) {
        ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) player.chunkCoordIntPairQueue.get(0);
        if (chunkcoordintpair == null) {
            return;
        }

        boolean canSend = player.netServerHandler.b() + chunkCompressionDispatchBridge.getPlayerQueueSize(player) < 4;
        if (!canSend) {
            return;
        }

        WorldServer worldserver = player.b.getWorldServer(player.dimension);
        player.chunkCoordIntPairQueue.remove(chunkcoordintpair);
        player.netServerHandler.sendPacket(new Packet51MapChunk(chunkcoordintpair.x * 16, 0, chunkcoordintpair.z * 16, 16, 128, 16, worldserver));

        Chunk chunk = player.world.getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
        player.getWorldServer().tracker.a(player, chunk);

        List list = worldserver.getTileEntities(
                chunkcoordintpair.x * 16,
                0,
                chunkcoordintpair.z * 16,
                chunkcoordintpair.x * 16 + 16,
                128,
                chunkcoordintpair.z * 16 + 16);

        for (int i = 0; i < list.size(); ++i) {
            sendTileEntityUpdate(player, (TileEntity) list.get(i));
        }
    }

    private void sendTileEntityUpdate(EntityPlayer player, TileEntity tileentity) {
        if (tileentity == null) {
            return;
        }
        Packet packet = tileentity.f();
        if (packet != null) {
            player.netServerHandler.sendPacket(packet);
        }
    }
}
