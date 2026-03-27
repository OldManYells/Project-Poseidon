package com.legacyminecraft.poseidon.world;

/**
 * Minimal packed nibble array used by chunk light and metadata storage.
 */
public class NibbleArray {
    private final byte[] values;

    public NibbleArray() {
        this(4096);
    }

    public NibbleArray(int logicalSize) {
        this.values = new byte[logicalSize >> 1];
    }

    public int a(int x, int y, int z) {
        int packedIndex = x << 11 | z << 7 | y;
        int byteIndex = packedIndex >> 1;
        int half = packedIndex & 1;
        return half == 0 ? values[byteIndex] & 15 : values[byteIndex] >> 4 & 15;
    }

    public void a(int x, int y, int z, int value) {
        int packedIndex = x << 11 | z << 7 | y;
        int byteIndex = packedIndex >> 1;
        int half = packedIndex & 1;
        if (half == 0) {
            values[byteIndex] = (byte) (values[byteIndex] & 240 | value & 15);
        } else {
            values[byteIndex] = (byte) (values[byteIndex] & 15 | (value & 15) << 4);
        }
    }
}
