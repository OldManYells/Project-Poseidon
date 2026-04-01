package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.EnumToolMaterial;
import com.legacyminecraft.poseidon.world.block.Block;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

public class ItemTool extends Item {

    private Block[] bk;
    private float bl = 4.0F;
    private int bm;
    protected EnumToolMaterial a;

    public ItemTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block[] ablock) {
        super(i);
        this.a = enumtoolmaterial;
        this.bk = ablock;
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
        this.bl = enumtoolmaterial.b();
        this.bm = j + enumtoolmaterial.c();
    }

    public float a(ItemStack itemstack, Block block) {
        for (int i = 0; i < this.bk.length; ++i) {
            if (this.bk[i] == block) {
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
