package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.block.Block;
import com.legacyminecraft.poseidon.entity.EntityHuman;
import com.legacyminecraft.poseidon.world.Entity;
import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical item scaffold used by migrated stack/map logic.
 */
public class Item {
    public static final Item[] byId = new Item[32000];
    public static final Item MAP = register(new Item(358));

    public final int id;
    private int maxStackSize = 64;
    private int maxDurability;
    private boolean usesData;
    private String name = "item";

    public Item(int id) {
        this.id = id;
    }

    private static Item register(Item item) {
        if (item.id >= 0 && item.id < byId.length) {
            byId[item.id] = item;
        }
        return item;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int e() {
        return maxDurability;
    }

    public boolean d() {
        return usesData;
    }

    public String a() {
        return name;
    }

    public boolean a(ItemStack stack, EntityHuman entityHuman, World world, int x, int y, int z, int face) {
        return false;
    }

    public ItemStack a(ItemStack stack, World world, EntityHuman entityHuman) {
        return stack;
    }

    public boolean a(ItemStack stack, com.legacyminecraft.poseidon.item.EntityLiving target, com.legacyminecraft.poseidon.item.EntityLiving attacker) {
        return false;
    }

    public boolean a(ItemStack stack, int x, int y, int z, int blockId, EntityHuman entityHuman) {
        return false;
    }

    public int a(Entity entity) {
        return 1;
    }

    public boolean a(Block block) {
        return false;
    }

    public void a(ItemStack stack, com.legacyminecraft.poseidon.item.EntityLiving livingEntity) {
    }

    public void a(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
    }

    public void c(ItemStack stack, World world, EntityHuman entityHuman) {
    }
}
