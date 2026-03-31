package net.minecraft.server;

public class RedstoneUpdateInfo {

    public int a;
    public int b;
    public int c;
    public long d;

    public RedstoneUpdateInfo(int i, int j, int k, long l) {
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public int getZ() {
        return this.c;
    }

    public long getUpdateTime() {
        return this.d;
    }

}
