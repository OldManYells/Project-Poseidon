package net.minecraft.server;

import com.legacyminecraft.poseidon.world.chunk.NibbleArrayBehaviour;

public class NibbleArray extends com.legacyminecraft.poseidon.world.NibbleArray {
    private static final NibbleArrayBehaviour NIBBLE_ARRAY_BEHAVIOUR = NibbleArrayBehaviour.getInstance();

    public final byte[] a;

    public NibbleArray(int i) {
        super(i);
        this.a = NIBBLE_ARRAY_BEHAVIOUR.createBackingArray(i);
    }

    public NibbleArray(byte[] abyte) {
        super(abyte.length << 1);
        this.a = abyte;
    }

    public int a(int i, int j, int k) {
        return NIBBLE_ARRAY_BEHAVIOUR.getValue(this.a, i, j, k);
    }

    public void a(int i, int j, int k, int l) {
        NIBBLE_ARRAY_BEHAVIOUR.setValue(this.a, i, j, k, l);
    }

    public boolean a() {
        return NIBBLE_ARRAY_BEHAVIOUR.hasBackingArray(this.a);
    }
}
