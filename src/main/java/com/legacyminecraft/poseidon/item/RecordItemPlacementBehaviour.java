package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;
import net.minecraft.server.BlockJukeBox;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public final class RecordItemPlacementBehaviour {
    private static final RecordItemPlacementBehaviour INSTANCE = new RecordItemPlacementBehaviour();

    private RecordItemPlacementBehaviour() {
    }

    public static RecordItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean placeRecord(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, int itemId) {
        if (world.getTypeId(i, j, k) != Block.JUKEBOX.id || world.getData(i, j, k) != 0) {
            return false;
        }
        if (world.isStatic) {
            return true;
        }

        ((BlockJukeBox) Block.JUKEBOX).f(world, i, j, k, itemId);
        world.a((EntityHuman) null, 1005, i, j, k, itemId);
        --itemstack.count;
        return true;
    }
}
