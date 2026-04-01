package com.legacyminecraft.poseidon.world.entity.data;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class WatchableObject {

    private final int a;
    private final int b;
    private Object c;
    private boolean d;

    public WatchableObject(int i, int j, Object object) {
        this.b = j;
        this.c = object;
        this.a = i;
        this.d = true;
    }

    public int a() {
        return this.b;
    }

    public void a(Object object) {
        this.c = object;
    }

    public Object b() {
        return this.c;
    }

    public int c() {
        return this.a;
    }

    public boolean d() {
        return this.d;
    }

    public void a(boolean flag) {
        this.d = flag;
    }
}
