package net.minecraft.server;

import com.legacyminecraft.poseidon.item.SaddleUseBehaviour;

public class ItemSaddle extends Item {
    private static final SaddleUseBehaviour SADDLE_USE_BEHAVIOUR = SaddleUseBehaviour.getInstance();

    public ItemSaddle(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    public void a(ItemStack itemstack, EntityLiving entityliving) {
        SADDLE_USE_BEHAVIOUR.applyToEntity(itemstack, entityliving);
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        return SADDLE_USE_BEHAVIOUR.interactEntity(itemstack, entityliving, entityliving1);
    }
}
