package net.minecraft.server;

import com.legacyminecraft.poseidon.world.NextTickEntryOrderingBehaviour;

public class NextTickListEntry implements Comparable {
    private static final NextTickEntryOrderingBehaviour NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR = NextTickEntryOrderingBehaviour.getInstance();

    private static long f = 0L;
    public int a;
    public int b;
    public int c;
    public int d;
    public long e;
    private long g;

    public NextTickListEntry(int i, int j, int k, int l) {
        this.g = NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.nextSequence(f);
        f = NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.incrementCounter(f);
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
    }

    public boolean equals(Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return false;
        } else {
            NextTickListEntry nextticklistentry = (NextTickListEntry) object;

            return NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.matches(this.a, this.b, this.c, this.d, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, nextticklistentry.d);
        }
    }

    public int hashCode() {
        return NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.hash(this.a, this.b, this.c, this.d);
    }

    public NextTickListEntry a(long i) {
        this.e = i;
        return this;
    }

    public int compareTo(Object o) {
        NextTickListEntry nextticklistentry = (NextTickListEntry) o;
        return NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.compare(this.e, this.g, nextticklistentry.e, nextticklistentry.g);
    }
}
