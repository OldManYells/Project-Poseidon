package org.bukkit.craftbukkit.item;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.EnumToolMaterial;
import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.craftbukkit.entity.EntityLiving;

public class ItemTool extends Item {

    private CraftBlock[] bk;
    private float bl = 4.0F;
    private int bm;
    protected EnumToolMaterial a;

    protected ItemTool(int i, int j, EnumToolMaterial enumtoolmaterial, CraftBlock[] ablock) {
        super(i);
        this.a = enumtoolmaterial;
        this.bk = ablock;
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
        this.bl = enumtoolmaterial.b();
        this.bm = j + enumtoolmaterial.c();
    }

    public float a(ItemStack itemstack, CraftBlock baseBlock) {
        for (int i = 0; i < this.bk.length; ++i) {
            if (this.bk[i] == baseBlock) {
                return this.bl;
            }
        }

        return 1.0F;
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        itemstack.damage(2, entityliving1);
        return true;
    }

    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        itemstack.damage(1, entityliving);
        return true;
    }

    public int a(Entity entity) {
        return this.bm;
    }
}
