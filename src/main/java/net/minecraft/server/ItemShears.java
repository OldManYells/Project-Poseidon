package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ShearsInteractionBehaviour;

public class ItemShears extends Item {
    private static final ShearsInteractionBehaviour SHEARS_INTERACTION_BEHAVIOUR = ShearsInteractionBehaviour.getInstance();

    public ItemShears(int i) {
        super(i);
        this.c(1);
        this.d(238);
    }

    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        if (SHEARS_INTERACTION_BEHAVIOUR.shouldDamageOnBlockBreak(i)) {
            itemstack.damage(1, entityliving);
        }

        return super.a(itemstack, i, j, k, l, entityliving);
    }

    public boolean a(Block block) {
        return SHEARS_INTERACTION_BEHAVIOUR.canHarvest(block);
    }

    public float a(ItemStack itemstack, Block block) {
        return SHEARS_INTERACTION_BEHAVIOUR.breakSpeedMultiplier(block, super.a(itemstack, block));
    }
}
