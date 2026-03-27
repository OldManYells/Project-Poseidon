package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation item-stack scaffold.
 */
public class ItemStack {
    public int id;
    public int count;
    public int damage;

    public ItemStack(Item item) {
        this(item.id, 1, 0);
    }

    public ItemStack(Item item, int count) {
        this(item.id, count, 0);
    }

    public ItemStack(Item item, int count, int damage) {
        this(item.id, count, damage);
    }

    public ItemStack(int id, int count, int damage) {
        this.id = id;
        this.count = count;
        this.damage = damage;
    }
}
