package com.legacyminecraft.poseidon.world.path;

import com.legacyminecraft.poseidon.world.PathPoint;

public final class PathHeapBehaviour {
    private static final PathHeapBehaviour INSTANCE = new PathHeapBehaviour();

    private static final int DEFAULT_CAPACITY = 1024;

    private PathHeapBehaviour() {
    }

    public static PathHeapBehaviour getInstance() {
        return INSTANCE;
    }

    public static final class InsertResult {
        public final PathPoint[] heap;
        public final int size;
        public final PathPoint inserted;

        public InsertResult(PathPoint[] heap, int size, PathPoint inserted) {
            this.heap = heap;
            this.size = size;
            this.inserted = inserted;
        }
    }

    public static final class PopResult {
        public final PathPoint[] heap;
        public final int size;
        public final PathPoint popped;

        public PopResult(PathPoint[] heap, int size, PathPoint popped) {
            this.heap = heap;
            this.size = size;
            this.popped = popped;
        }
    }

    public PathPoint[] createHeap() {
        return new PathPoint[DEFAULT_CAPACITY];
    }

    public InsertResult insert(PathPoint[] heap, int size, PathPoint pathpoint) {
        if (pathpoint.poseidonGetHeapIndex() >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }

        if (size == heap.length) {
            PathPoint[] resized = new PathPoint[size << 1];
            System.arraycopy(heap, 0, resized, 0, size);
            heap = resized;
        }

        heap[size] = pathpoint;
        pathpoint.poseidonSetHeapIndex(size);
        int newSize = size + 1;
        this.siftUp(heap, newSize, pathpoint.poseidonGetHeapIndex());
        return new InsertResult(heap, newSize, pathpoint);
    }

    public int clear() {
        return 0;
    }

    public PopResult pop(PathPoint[] heap, int size) {
        PathPoint pathpoint = heap[0];
        int newSize = size - 1;
        heap[0] = heap[newSize];
        heap[newSize] = null;

        if (newSize > 0) {
            this.siftDown(heap, newSize, 0);
        }

        pathpoint.poseidonSetHeapIndex(-1);
        return new PopResult(heap, newSize, pathpoint);
    }

    public void updatePriority(PathPoint[] heap, int size, PathPoint pathpoint, float newPriority) {
        float previous = pathpoint.poseidonGetPriority();
        pathpoint.poseidonSetPriority(newPriority);
        if (newPriority < previous) {
            this.siftUp(heap, size, pathpoint.poseidonGetHeapIndex());
        } else {
            this.siftDown(heap, size, pathpoint.poseidonGetHeapIndex());
        }
    }

    public boolean isEmpty(int size) {
        return size == 0;
    }

    private void siftUp(PathPoint[] heap, int size, int index) {
        PathPoint pathpoint = heap[index];
        float priority = pathpoint.poseidonGetPriority();

        int parentIndex;
        for (; index > 0; index = parentIndex) {
            parentIndex = index - 1 >> 1;
            PathPoint parent = heap[parentIndex];

            if (priority >= parent.poseidonGetPriority()) {
                break;
            }

            heap[index] = parent;
            parent.poseidonSetHeapIndex(index);
        }

        heap[index] = pathpoint;
        pathpoint.poseidonSetHeapIndex(index);
    }

    private void siftDown(PathPoint[] heap, int size, int index) {
        PathPoint pathpoint = heap[index];
        float priority = pathpoint.poseidonGetPriority();

        while (true) {
            int left = 1 + (index << 1);
            int right = left + 1;

            if (left >= size) {
                break;
            }

            PathPoint leftNode = heap[left];
            float leftPriority = leftNode.poseidonGetPriority();
            PathPoint rightNode;
            float rightPriority;

            if (right >= size) {
                rightNode = null;
                rightPriority = Float.POSITIVE_INFINITY;
            } else {
                rightNode = heap[right];
                rightPriority = rightNode.poseidonGetPriority();
            }

            if (leftPriority < rightPriority) {
                if (leftPriority >= priority) {
                    break;
                }

                heap[index] = leftNode;
                leftNode.poseidonSetHeapIndex(index);
                index = left;
            } else {
                if (rightPriority >= priority) {
                    break;
                }

                heap[index] = rightNode;
                rightNode.poseidonSetHeapIndex(index);
                index = right;
            }
        }

        heap[index] = pathpoint;
        pathpoint.poseidonSetHeapIndex(index);
    }
}
