package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet51MapChunk;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Canonical behavior for ChunkCompressionThread queue loop, queue-size accounting, and packet routing decisions.
 */
public final class ChunkCompressionQueueBehaviour {
    private static final ChunkCompressionQueueBehaviour INSTANCE = new ChunkCompressionQueueBehaviour();

    private ChunkCompressionQueueBehaviour() {
    }

    public static ChunkCompressionQueueBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> void runLoop(BlockingQueue<T> queue, QueueItemHandler<T> queueItemHandler, ExceptionHandler exceptionHandler) {
        while (true) {
            try {
                queueItemHandler.handle(queue.take());
            } catch (InterruptedException ignored) {
            } catch (Exception exception) {
                exceptionHandler.handle(exception);
            }
        }
    }

    public boolean shouldCompress(Packet packet) {
        return packet instanceof Packet51MapChunk;
    }

    public void updateQueueSize(Map<EntityPlayer, Integer> queueSizeByPlayer, EntityPlayer player, int amountDelta) {
        synchronized (queueSizeByPlayer) {
            Integer currentCount = queueSizeByPlayer.get(player);
            int updatedCount = amountDelta + (currentCount == null ? 0 : currentCount.intValue());
            if (updatedCount == 0) {
                queueSizeByPlayer.remove(player);
                return;
            }
            queueSizeByPlayer.put(player, updatedCount);
        }
    }

    public int getQueueSize(Map<EntityPlayer, Integer> queueSizeByPlayer, EntityPlayer player) {
        synchronized (queueSizeByPlayer) {
            Integer currentCount = queueSizeByPlayer.get(player);
            return currentCount == null ? 0 : currentCount.intValue();
        }
    }

    public <T> void enqueueRetry(BlockingQueue<T> queue, T task) {
        while (true) {
            try {
                queue.put(task);
                return;
            } catch (InterruptedException ignored) {
            }
        }
    }

    public interface QueueItemHandler<T> {
        void handle(T item);
    }

    public interface ExceptionHandler {
        void handle(Exception exception);
    }
}

