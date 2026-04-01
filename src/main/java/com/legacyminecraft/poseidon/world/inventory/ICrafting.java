package com.legacyminecraft.poseidon.world.inventory;

import com.legacyminecraft.poseidon.world.item.ItemStack;

import java.util.List;

public interface ICrafting {

    void a(Container container, List list);

    void a(Container container, int i, ItemStack itemstack);

    void a(Container container, int i, int j);
}
