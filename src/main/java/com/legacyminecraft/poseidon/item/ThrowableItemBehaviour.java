package com.legacyminecraft.poseidon.item;


public final class ThrowableItemBehaviour {
    private static final ThrowableItemBehaviour INSTANCE = new ThrowableItemBehaviour();

    private ThrowableItemBehaviour() {
    }

    public static ThrowableItemBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack throwEgg(ItemStack itemstack, World world, EntityHuman entityhuman, java.util.Random random) {
        return throwEntity(itemstack, world, entityhuman, new EntityEgg(world, entityhuman), random);
    }

    public ItemStack throwSnowball(ItemStack itemstack, World world, EntityHuman entityhuman, java.util.Random random) {
        return throwEntity(itemstack, world, entityhuman, new EntitySnowball(world, entityhuman), random);
    }

    public ItemStack throwEntity(ItemStack itemstack, World world, EntityHuman entityhuman, Entity entity, java.util.Random random) {
        --itemstack.count;
        world.makeSound(entityhuman, "random.bow", 0.5F, resolveThrowPitch(random));
        if (!world.isStatic) {
            world.addEntity(entity);
        }
        return itemstack;
    }

    public float resolveThrowPitch(java.util.Random random) {
        return 0.4F / (random.nextFloat() * 0.4F + 0.8F);
    }
}
