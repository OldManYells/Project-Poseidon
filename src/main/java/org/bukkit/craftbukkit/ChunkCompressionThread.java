package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.compat.bukkit.ChunkMapPacketCompressionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.ChunkCompressionQueueBehaviour;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet51MapChunk;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.Deflater;

public final class ChunkCompressionThread implements Runnable {

    private static final ChunkCompressionThread instance = new ChunkCompressionThread();
    private static boolean isRunning = false;

    private final int QUEUE_CAPACITY = 1024 * 10;
    private final HashMap<EntityPlayer, Integer> queueSizePerPlayer = new HashMap<EntityPlayer, Integer>();
    private final BlockingQueue<QueuedPacket> packetQueue = new LinkedBlockingQueue<QueuedPacket>(QUEUE_CAPACITY);

    private final int CHUNK_SIZE = 16 * 128 * 16 * 5 / 2;
    private final int REDUCED_DEFLATE_THRESHOLD = CHUNK_SIZE / 4;
    private final int DEFLATE_LEVEL_CHUNKS = 6;
    private final int DEFLATE_LEVEL_PARTS = 1;

    private final Deflater deflater = new Deflater();
    private byte[] deflateBuffer = new byte[CHUNK_SIZE + 100];
    private final ChunkMapPacketCompressionBehaviour chunkMapPacketCompressionBehaviour =
            ChunkMapPacketCompressionBehaviour.getInstance();
    private final ChunkCompressionQueueBehaviour chunkCompressionQueueBehaviour =
            ChunkCompressionQueueBehaviour.getInstance();

    public static void startThread() {
        if (!isRunning) {
            isRunning = true;
            new Thread(instance).start();
        }
    }

    public void run() {
        chunkCompressionQueueBehaviour.runLoop(
                packetQueue,
                new ChunkCompressionQueueBehaviour.QueueItemHandler<QueuedPacket>() {
                    public void handle(QueuedPacket queuedPacket) {
                        handleQueuedPacket(queuedPacket);
                    }
                },
                new ChunkCompressionQueueBehaviour.ExceptionHandler() {
                    public void handle(Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

    private void handleQueuedPacket(QueuedPacket queuedPacket) {
        addToPlayerQueueSize(queuedPacket.player, -1);
        // Compress the packet if necessary.
        if (queuedPacket.compress) {
            handleMapChunk(queuedPacket);
        }
        sendToNetworkQueue(queuedPacket);
    }

    private void handleMapChunk(QueuedPacket queuedPacket) {
        Packet51MapChunk packet = (Packet51MapChunk) queuedPacket.packet;

        // If 'packet.g' is set then this packet has already been compressed.
        if (packet.g != null) {
            return;
        }

        ChunkMapPacketCompressionBehaviour.CompressionResult compressionResult =
                chunkMapPacketCompressionBehaviour.compress(
                        packet.rawData,
                        deflater,
                        deflateBuffer,
                        REDUCED_DEFLATE_THRESHOLD,
                        DEFLATE_LEVEL_CHUNKS,
                        DEFLATE_LEVEL_PARTS
                );
        deflateBuffer = compressionResult.getOutputBuffer();
        int compressedSize = compressionResult.getCompressedSize();

        // copy compressed data to packet
        packet.g = new byte[compressedSize];
        packet.h = compressedSize;
        System.arraycopy(deflateBuffer, 0, packet.g, 0, compressedSize);
    }

    private void sendToNetworkQueue(QueuedPacket queuedPacket) {
        queuedPacket.player.netServerHandler.networkManager.queue(queuedPacket.packet);
    }

    public static void sendPacket(EntityPlayer player, Packet packet) {
        if (instance.chunkCompressionQueueBehaviour.shouldCompress(packet)) {
            // MapChunk Packets need compressing.
            instance.addQueuedPacket(new QueuedPacket(player, packet, true));
        } else {
            // Other Packets don't.
            instance.addQueuedPacket(new QueuedPacket(player, packet, false));
        }
    }

    private void addToPlayerQueueSize(EntityPlayer player, int amount) {
        chunkCompressionQueueBehaviour.updateQueueSize(queueSizePerPlayer, player, amount);
    }

    public static int getPlayerQueueSize(EntityPlayer player) {
        return instance.chunkCompressionQueueBehaviour.getQueueSize(instance.queueSizePerPlayer, player);
    }

    private void addQueuedPacket(QueuedPacket task) {
        addToPlayerQueueSize(task.player, +1);
        chunkCompressionQueueBehaviour.enqueueRetry(packetQueue, task);
    }

    private static class QueuedPacket {
        final EntityPlayer player;
        final Packet packet;
        final boolean compress;

        QueuedPacket(EntityPlayer player, Packet packet, boolean compress) {
            this.player = player;
            this.packet = packet;
            this.compress = compress;
        }
    }
}
