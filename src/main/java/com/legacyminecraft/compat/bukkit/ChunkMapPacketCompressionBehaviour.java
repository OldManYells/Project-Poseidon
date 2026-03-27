package com.legacyminecraft.compat.bukkit;

import java.util.zip.Deflater;

/**
 * Canonical behavior for Packet51 map-chunk compression settings and buffer management.
 */
public final class ChunkMapPacketCompressionBehaviour {
    private static final ChunkMapPacketCompressionBehaviour INSTANCE = new ChunkMapPacketCompressionBehaviour();

    private ChunkMapPacketCompressionBehaviour() {
    }

    public static ChunkMapPacketCompressionBehaviour getInstance() {
        return INSTANCE;
    }

    public CompressionResult compress(
            byte[] rawData,
            Deflater deflater,
            byte[] deflateBuffer,
            int reducedDeflateThreshold,
            int deflateLevelChunks,
            int deflateLevelParts
    ) {
        int dataSize = rawData.length;
        byte[] outputBuffer = ensureOutputBuffer(deflateBuffer, dataSize + 100);

        deflater.reset();
        deflater.setLevel(dataSize < reducedDeflateThreshold ? deflateLevelParts : deflateLevelChunks);
        deflater.setInput(rawData);
        deflater.finish();

        int compressedSize = deflater.deflate(outputBuffer);
        if (compressedSize == 0) {
            compressedSize = deflater.deflate(outputBuffer);
        }

        return new CompressionResult(outputBuffer, compressedSize);
    }

    private byte[] ensureOutputBuffer(byte[] currentBuffer, int requiredCapacity) {
        if (currentBuffer.length < requiredCapacity) {
            return new byte[requiredCapacity];
        }
        return currentBuffer;
    }

    public static final class CompressionResult {
        private final byte[] outputBuffer;
        private final int compressedSize;

        private CompressionResult(byte[] outputBuffer, int compressedSize) {
            this.outputBuffer = outputBuffer;
            this.compressedSize = compressedSize;
        }

        public byte[] getOutputBuffer() {
            return outputBuffer;
        }

        public int getCompressedSize() {
            return compressedSize;
        }
    }
}

