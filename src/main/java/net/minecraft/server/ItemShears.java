package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityLiving;

public class ItemShears extends Item {

    public ItemShears(int i) {
        super(i);
        this.c(1);
        this.d(238);
    }

    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        if (i == CraftBlock.LEAVES.id || i == CraftBlock.WEB.id) {
            itemstack.damage(1, entityliving);
        }

        return super.a(itemstack, i, j, k, l, entityliving);
    }

    public boolean a(CraftBlock baseBlock) {
        return baseBlock.id == CraftBlock.WEB.id;
    }

    public float a(ItemStack itemstack, CraftBlock baseBlock) {
        return baseBlock.id != CraftBlock.WEB.id && baseBlock.id != CraftBlock.LEAVES.id ? (baseBlock.id == CraftBlock.WOOL.id ? 5.0F : super.a(itemstack, baseBlock)) : 15.0F;
    }
}
