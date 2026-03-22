package com.legacyminecraft.poseidon.world;

public final class NextTickEntryOrderingBehaviour {
    private static final NextTickEntryOrderingBehaviour INSTANCE = new NextTickEntryOrderingBehaviour();

    private NextTickEntryOrderingBehaviour() {
    }

    public static NextTickEntryOrderingBehaviour getInstance() {
        return INSTANCE;
    }

    public long nextSequence(long currentCounter) {
        return currentCounter;
    }

    public long incrementCounter(long currentCounter) {
        return currentCounter + 1L;
    }

    public boolean matches(int a, int b, int c, int d, int otherA, int otherB, int otherC, int otherD) {
        return a == otherA && b == otherB && c == otherC && d == otherD;
    }

    public int hash(int a, int b, int c, int d) {
        return (a * 128 * 1024 + c * 128 + b) * 256 + d;
    }

    public int compare(long e, long g, long otherE, long otherG) {
        return e < otherE ? -1 : (e > otherE ? 1 : (g < otherG ? -1 : (g > otherG ? 1 : 0)));
    }
}
