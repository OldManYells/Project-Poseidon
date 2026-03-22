package com.legacyminecraft.poseidon.world.chunk;

public final class NibbleArrayBehaviour {
    private static final NibbleArrayBehaviour INSTANCE = new NibbleArrayBehaviour();

    private NibbleArrayBehaviour() {
    }

    public static NibbleArrayBehaviour getInstance() {
        return INSTANCE;
    }

    public byte[] createBackingArray(int logicalSize) {
        return new byte[logicalSize >> 1];
    }

    public int getValue(byte[] values, int x, int y, int z) {
        int packedIndex = x << 11 | z << 7 | y;
        int byteIndex = packedIndex >> 1;
        int half = packedIndex & 1;

        return half == 0 ? values[byteIndex] & 15 : values[byteIndex] >> 4 & 15;
    }

    public void setValue(byte[] values, int x, int y, int z, int nibbleValue) {
        int packedIndex = x << 11 | z << 7 | y;
        int byteIndex = packedIndex >> 1;
        int half = packedIndex & 1;

        if (half == 0) {
            values[byteIndex] = (byte) (values[byteIndex] & 240 | nibbleValue & 15);
        } else {
            values[byteIndex] = (byte) (values[byteIndex] & 15 | (nibbleValue & 15) << 4);
        }
    }

    public boolean hasBackingArray(byte[] values) {
        return values != null;
    }
}
