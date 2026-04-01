package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

public class ItemSaddle extends Item {

    public ItemSaddle(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    public void a(ItemStack itemstack, EntityLiving entityliving) {
        if (entityliving instanceof EntityPig) {
            EntityPig entitypig = (EntityPig) entityliving;

            if (!entitypig.hasSaddle()) {
                entitypig.setSaddle(true);
                --itemstack.count;
            }
        }
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        this.a(itemstack, entityliving);
        return true;
    }
}
