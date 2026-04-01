package com.legacyminecraft.poseidon.world.core;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.server.network.*;
import com.legacyminecraft.poseidon.world.block.*;
import com.legacyminecraft.poseidon.world.block.entity.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.generation.*;
import com.legacyminecraft.poseidon.world.inventory.*;
import com.legacyminecraft.poseidon.world.item.*;
import com.legacyminecraft.poseidon.world.map.*;
import com.legacyminecraft.poseidon.world.storage.*;
import com.legacyminecraft.poseidon.world.storage.nbt.*;

public class NextTickListEntry implements Comparable {

    private static long f = 0L;
    public int a;
    public int b;
    public int c;
    public int d;
    public long e;
    private long g;

    public NextTickListEntry(int i, int j, int k, int l) {
        this.g = (long) (f++);
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

            return this.a == nextticklistentry.a && this.b == nextticklistentry.b && this.c == nextticklistentry.c && this.d == nextticklistentry.d;
        }
    }

    public int hashCode() {
        return (this.a * 128 * 1024 + this.c * 128 + this.b) * 256 + this.d;
    }

    public NextTickListEntry a(long i) {
        this.e = i;
        return this;
    }

    public int compareTo(Object o) {
        NextTickListEntry nextticklistentry = (NextTickListEntry) o;
        return this.e < nextticklistentry.e ? -1 : (this.e > nextticklistentry.e ? 1 : (this.g < nextticklistentry.g ? -1 : (this.g > nextticklistentry.g ? 1 : 0)));
    }
}
