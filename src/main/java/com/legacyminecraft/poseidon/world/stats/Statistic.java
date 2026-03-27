package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal statistic base model.
 */
public class Statistic {
    public static final Counter j = new Counter() {
        @Override
        public String format(int value) {
            return Integer.toString(value);
        }
    };
    public static final Counter k = new Counter() {
        @Override
        public String format(int value) {
            return Integer.toString(value);
        }
    };

    public final int e;
    public final String f;
    public String h;
    private final Counter counter;

    public Statistic(int id, String name) {
        this(id, name, null);
    }

    public Statistic(int id, String name, Counter counter) {
        this.e = id;
        this.f = name;
        this.counter = counter;
    }

    public Statistic e() {
        return this;
    }

    public Statistic d() {
        return this;
    }

    public Counter getCounter() {
        return counter;
    }
}
