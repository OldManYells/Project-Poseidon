package com.legacyminecraft.poseidon.world.item;

import com.legacyminecraft.poseidon.world.block.BlockMinecartTrack;
import com.legacyminecraft.poseidon.world.core.World;
import com.legacyminecraft.poseidon.world.entity.EntityHuman;
import com.legacyminecraft.poseidon.world.entity.EntityMinecart;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemMinecart extends Item {

    public int a;

    public ItemMinecart(int i, int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        int i1 = world.getTypeId(i, j, k);

        if (BlockMinecartTrack.c(i1)) {
            if (!world.isStatic) {
                PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);

                if (event.isCancelled()) {
                    return false;
                }

                world.addEntity(new EntityMinecart(world, (double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), this.a));
            }

            --itemstack.count;
            return true;
        } else {
            return false;
        }
    }
}
