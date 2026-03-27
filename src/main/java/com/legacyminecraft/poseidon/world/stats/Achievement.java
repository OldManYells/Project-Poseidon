package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal achievement model used by stat bootstrap code.
 */
public final class Achievement extends Statistic {
    private final int x;
    private final int y;
    private final Object trigger;
    private final Achievement parent;

    public Achievement(int id, String key, int x, int y, Object trigger, Achievement parent) {
        super(id, key);
        this.x = x;
        this.y = y;
        this.trigger = trigger;
        this.parent = parent;
    }

    public Achievement a() {
        return this;
    }

    public Achievement b() {
        return this;
    }

    public Achievement c() {
        return this;
    }

    public Achievement d() {
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Object getTrigger() {
        return trigger;
    }

    public Achievement getParent() {
        return parent;
    }
}
