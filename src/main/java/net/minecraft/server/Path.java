package net.minecraft.server;

public class Path {

    private PathPoint[] points = new PathPoint[1024];
    private int count = 0;

    public Path() {}

    public PathPoint addPoint(PathPoint pathPoint) {
        if (pathPoint.d >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }

        if (this.count == this.points.length) {
            PathPoint[] expanded = new PathPoint[this.count << 1];
            System.arraycopy(this.points, 0, expanded, 0, this.count);
            this.points = expanded;
        }

        this.points[this.count] = pathPoint;
        pathPoint.d = this.count;
        this.sortBack(this.count++);
        return pathPoint;
    }

    public void clearPath() {
        this.count = 0;
    }

    public PathPoint dequeue() {
        PathPoint first = this.points[0];
        this.points[0] = this.points[--this.count];
        this.points[this.count] = null;

        if (this.count > 0) {
            this.sortForward(0);
        }

        first.d = -1;
        return first;
    }

    public void changeDistance(PathPoint pathPoint, float distance) {
        float previousDistance = pathPoint.g;
        pathPoint.g = distance;
        if (distance < previousDistance) {
            this.sortBack(pathPoint.d);
        } else {
            this.sortForward(pathPoint.d);
        }
    }

    private void sortBack(int index) {
        PathPoint pathPoint = this.points[index];
        int parentIndex;

        for (float distance = pathPoint.g; index > 0; index = parentIndex) {
            parentIndex = index - 1 >> 1;
            PathPoint parent = this.points[parentIndex];

            if (distance >= parent.g) {
                break;
            }

            this.points[index] = parent;
            parent.d = index;
        }

        this.points[index] = pathPoint;
        pathPoint.d = index;
    }

    private void sortForward(int index) {
        PathPoint pathPoint = this.points[index];
        float distance = pathPoint.g;

        while (true) {
            int leftChild = 1 + (index << 1);
            int rightChild = leftChild + 1;

            if (leftChild >= this.count) {
                break;
            }

            PathPoint leftPoint = this.points[leftChild];
            float leftDistance = leftPoint.g;
            PathPoint rightPoint;
            float rightDistance;

            if (rightChild >= this.count) {
                rightPoint = null;
                rightDistance = Float.POSITIVE_INFINITY;
            } else {
                rightPoint = this.points[rightChild];
                rightDistance = rightPoint.g;
            }

            if (leftDistance < rightDistance) {
                if (leftDistance >= distance) {
                    break;
                }

                this.points[index] = leftPoint;
                leftPoint.d = index;
                index = leftChild;
            } else {
                if (rightDistance >= distance) {
                    break;
                }

                this.points[index] = rightPoint;
                rightPoint.d = index;
                index = rightChild;
            }
        }

        this.points[index] = pathPoint;
        pathPoint.d = index;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    @Deprecated
    public PathPoint a(PathPoint pathPoint) {
        return this.addPoint(pathPoint);
    }

    @Deprecated
    public void a() {
        this.clearPath();
    }

    @Deprecated
    public PathPoint b() {
        return this.dequeue();
    }

    @Deprecated
    public void a(PathPoint pathPoint, float distance) {
        this.changeDistance(pathPoint, distance);
    }

    @Deprecated
    private void a(int index) {
        this.sortBack(index);
    }

    @Deprecated
    private void b(int index) {
        this.sortForward(index);
    }

    @Deprecated
    public boolean c() {
        return this.isEmpty();
    }
}
