package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.entity.EntityHuman;
import com.legacyminecraft.poseidon.item.ItemStack;

/**
 * Canonical world-map scaffold for migrated item/map behaviours.
 */
public class WorldMap extends WorldMapBase {
    public int b;
    public int c;
    public byte map;
    public byte e;
    public byte[] f = new byte[16384];
    public int g;
    public final Object mapView = new Object();

    public WorldMap(String id) {
        super(id);
    }

    public void a(EntityHuman entityHuman, ItemStack itemStack) {
    }

    public byte[] a(ItemStack itemStack, World world, EntityHuman entityHuman) {
        return null;
    }

    public void a(int x, int minY, int maxY) {
    }
}
