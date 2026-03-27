package net.minecraft.server;

import java.util.Random;

public abstract class WorldGenerator {
    protected double a = 1.0D;
    protected double b = 1.0D;
    protected double c = 1.0D;

    public WorldGenerator() {
    }

    public abstract boolean a(World world, Random random, int i, int j, int k);

    public void a(double d0, double d1, double d2) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
    }
}
