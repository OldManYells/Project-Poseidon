package com.legacyminecraft.poseidon.world;

/**
 * Canonical behaviour for extracting packed chunk block/light/metadata sections into an output buffer.
 */
public final class ChunkDataExtractionBehaviour {
    private static final ChunkDataExtractionBehaviour INSTANCE = new ChunkDataExtractionBehaviour();

    private ChunkDataExtractionBehaviour() {
    }

    public static ChunkDataExtractionBehaviour getInstance() {
        return INSTANCE;
    }

    public int copyChunkData(byte[] blocks, byte[] metadataNibble, byte[] skylightNibble, byte[] blocklightNibble,
                             byte[] out, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int outOffset) {
        int width = maxX - minX;
        int height = maxY - minY;
        int depth = maxZ - minZ;

        if (width * height * depth == blocks.length) {
            System.arraycopy(blocks, 0, out, outOffset, blocks.length);
            outOffset += blocks.length;
            System.arraycopy(metadataNibble, 0, out, outOffset, metadataNibble.length);
            outOffset += metadataNibble.length;
            System.arraycopy(skylightNibble, 0, out, outOffset, skylightNibble.length);
            outOffset += skylightNibble.length;
            System.arraycopy(blocklightNibble, 0, out, outOffset, blocklightNibble.length);
            outOffset += blocklightNibble.length;
            return outOffset;
        }

        for (int x = minX; x < maxX; ++x) {
            for (int z = minZ; z < maxZ; ++z) {
                int sourceIndex = x << 11 | z << 7 | minY;
                int copyLength = maxY - minY;
                System.arraycopy(blocks, sourceIndex, out, outOffset, copyLength);
                outOffset += copyLength;
            }
        }

        outOffset = copyNibbleSections(metadataNibble, out, minX, minY, minZ, maxX, maxY, maxZ, outOffset);
        outOffset = copyNibbleSections(skylightNibble, out, minX, minY, minZ, maxX, maxY, maxZ, outOffset);
        outOffset = copyNibbleSections(blocklightNibble, out, minX, minY, minZ, maxX, maxY, maxZ, outOffset);
        return outOffset;
    }

    private int copyNibbleSections(byte[] sourceNibble, byte[] out, int minX, int minY, int minZ,
                                   int maxX, int maxY, int maxZ, int outOffset) {
        for (int x = minX; x < maxX; ++x) {
            for (int z = minZ; z < maxZ; ++z) {
                int sourceIndex = (x << 11 | z << 7 | minY) >> 1;
                int copyLength = (maxY - minY) / 2;
                System.arraycopy(sourceNibble, sourceIndex, out, outOffset, copyLength);
                outOffset += copyLength;
            }
        }
        return outOffset;
    }
}

