package com.legacyminecraft.poseidon.entity;

import java.util.Random;

/**
 * Entity-local minecart scaffold.
 */
public class EntityMinecart extends Entity implements IInventory {
    public int type;
    public int c;
    public int b;
    public int damage;
    public int e;
    public double f;
    public double g;
    public double maxSpeed = 0.4D;
    public boolean slowWhenEmpty = true;
    public double flyingX = 0.95D;
    public double flyingY = 0.95D;
    public double flyingZ = 0.95D;
    public double derailedX = 0.5D;
    public double derailedY = 0.5D;
    public double derailedZ = 0.5D;

    private final ItemStack[] items = new ItemStack[36];
    private final Random random = new Random();

    public EntityMinecart() {
    }

    public EntityMinecart(World world, double x, double y, double z, int type) {
        this.world = world;
        this.type = type;
        this.setPosition(x, y, z);
    }

    public void poseidonMarkDamaged() {
    }

    public void poseidonDropEntityItem(int itemId, int count, float offset) {
    }

    public float poseidonRandomFloat() {
        return random.nextFloat();
    }

    public int poseidonRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public double poseidonRandomGaussian() {
        return random.nextGaussian();
    }

    public double poseidonCollisionReductionFactor() {
        return 1.0D;
    }

    public void poseidonApplyCollisionPush(double x, double y, double z) {
        this.motX += x;
        this.motY += y;
        this.motZ += z;
    }

    @Override
    public int getSize() {
        return items.length;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < items.length ? items[index] : null;
    }

    @Override
    public ItemStack splitStack(int index, int amount) {
        ItemStack stack = getItem(index);
        if (stack == null) {
            return null;
        }
        return stack.a(amount);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        if (index >= 0 && index < items.length) {
            items[index] = itemStack;
        }
    }

    @Override
    public String getName() {
        return "Minecart";
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean canPlayerUse(EntityHuman entityHuman) {
        return true;
    }

    @Override
    public ItemStack[] getContents() {
        return items;
    }
}
