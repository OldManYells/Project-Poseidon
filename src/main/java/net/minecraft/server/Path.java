package net.minecraft.server;

import com.legacyminecraft.poseidon.world.path.PathHeapBehaviour;

public class Path {
    private static final PathHeapBehaviour PATH_HEAP_BEHAVIOUR = PathHeapBehaviour.getInstance();

    private PathPoint[] a = PATH_HEAP_BEHAVIOUR.createHeap();
    private int b = 0;

    public Path() {}

    public PathPoint a(PathPoint pathpoint) {
        PathHeapBehaviour.InsertResult result = PATH_HEAP_BEHAVIOUR.insert(this.a, this.b, pathpoint);
        this.a = result.heap;
        this.b = result.size;
        return result.inserted;
    }

    public void a() {
        this.b = PATH_HEAP_BEHAVIOUR.clear();
    }

    public PathPoint b() {
        PathHeapBehaviour.PopResult result = PATH_HEAP_BEHAVIOUR.pop(this.a, this.b);
        this.a = result.heap;
        this.b = result.size;
        return result.popped;
    }

    public void a(PathPoint pathpoint, float f) {
        PATH_HEAP_BEHAVIOUR.updatePriority(this.a, this.b, pathpoint, f);
    }

    public boolean c() {
        return PATH_HEAP_BEHAVIOUR.isEmpty(this.b);
    }
}
