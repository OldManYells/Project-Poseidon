package com.legacyminecraft.poseidon.item;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPig;
import net.minecraft.server.ItemStack;

public final class SaddleUseBehaviour {
    private static final SaddleUseBehaviour INSTANCE = new SaddleUseBehaviour();

    private SaddleUseBehaviour() {
    }

    public static SaddleUseBehaviour getInstance() {
        return INSTANCE;
    }

    public void applyToEntity(ItemStack itemstack, EntityLiving entityliving) {
        if (!(entityliving instanceof EntityPig)) {
            return;
        }

        EntityPig entitypig = (EntityPig) entityliving;
        if (!entitypig.hasSaddle()) {
            entitypig.setSaddle(true);
            --itemstack.count;
        }
    }

    public boolean interactEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        applyToEntity(itemstack, entityliving);
        return true;
    }
}
