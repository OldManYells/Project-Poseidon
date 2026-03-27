package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.world.CoordinateMathBehaviour;

public class PathPoint {
    private static final CoordinateMathBehaviour COORDINATE_MATH_BEHAVIOUR = CoordinateMathBehaviour.getInstance();

    public final int a;
    public final int b;
    public final int c;
    private final int j;
    int d = -1;
    float e;
    float f;
    float g;
    PathPoint h;
    public boolean i = false;

    public PathPoint(int i, int j, int k) {
        this.a = i;
        this.b = j;
        this.c = k;
        this.j = COORDINATE_MATH_BEHAVIOUR.pathPointKey(i, j, k);
    }

    public static int a(int i, int j, int k) {
        return COORDINATE_MATH_BEHAVIOUR.pathPointKey(i, j, k);
    }

    public float a(PathPoint pathpoint) {
        return COORDINATE_MATH_BEHAVIOUR.distance(this, pathpoint);
    }

    public boolean equals(Object object) {
        return COORDINATE_MATH_BEHAVIOUR.equals(this, object);
    }

    public int hashCode() {
        return this.j;
    }

    public boolean a() {
        return COORDINATE_MATH_BEHAVIOUR.isAssigned(this, this.d);
    }

    public final int poseidonGetHeapIndex() {
        return this.d;
    }

    public final void poseidonSetHeapIndex(int heapIndex) {
        this.d = heapIndex;
    }

    public final float poseidonGetPriority() {
        return this.g;
    }

    public final void poseidonSetPriority(float priority) {
        this.g = priority;
    }

    public final PathPoint poseidonGetPrevious() {
        return this.h;
    }

    public final void poseidonSetPrevious(PathPoint previous) {
        this.h = previous;
    }

    public String toString() {
        return COORDINATE_MATH_BEHAVIOUR.stringify(this);
    }
}
