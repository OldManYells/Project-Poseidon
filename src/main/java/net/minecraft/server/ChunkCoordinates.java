package net.minecraft.server;

public class ChunkCoordinates implements Comparable {
    public int x;
    public int y;
    public int z;

    public ChunkCoordinates() {}

    public ChunkCoordinates(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public ChunkCoordinates(ChunkCoordinates chunkcoordinates) {
        this.x = chunkcoordinates.x;
        this.y = chunkcoordinates.y;
        this.z = chunkcoordinates.z;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ChunkCoordinates)) {
            return false;
        }
        ChunkCoordinates other = (ChunkCoordinates) object;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    public int hashCode() {
        return (this.x * 31 + this.y) * 31 + this.z;
    }

    public int compareTo(Object o) {
        ChunkCoordinates chunkcoordinates = (ChunkCoordinates) o;
        if (this.y != chunkcoordinates.y) {
            return this.y - chunkcoordinates.y;
        }
        if (this.z != chunkcoordinates.z) {
            return this.z - chunkcoordinates.z;
        }
        return this.x - chunkcoordinates.x;
    }

    public double a(int i, int j, int k) {
        double deltaX = (double) (this.x - i);
        double deltaY = (double) (this.y - j);
        double deltaZ = (double) (this.z - k);
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }
}
