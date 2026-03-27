package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.PoseidonConfig;

import java.util.Random;

public final class BowUseBehaviour {
    private static final BowUseBehaviour INSTANCE = new BowUseBehaviour();

    private BowUseBehaviour() {
    }

    public static BowUseBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack use(ItemStack itemstack, World world, EntityHuman entityhuman, Random random) {
        if (!entityhuman.inventory.b(Item.ARROW.id)) {
            return itemstack;
        }

        if ((boolean) PoseidonConfig.getInstance().getProperty("world.settings.skeleton-shooting-sound-fix.enabled")) {
            world.a(entityhuman, 1002,
                    MathHelper.floor(entityhuman.locX),
                    MathHelper.floor(entityhuman.locY - (double) entityhuman.height),
                    MathHelper.floor(entityhuman.locZ),
                    0);
        } else {
            world.makeSound(entityhuman, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        }

        if (!world.isStatic) {
            world.addEntity(new EntityArrow(world, entityhuman));
        }

        return itemstack;
    }
}
