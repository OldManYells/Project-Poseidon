package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.compat.bukkit.NetServerHandler;

import java.util.List;

/**
 * Inventory-local player scaffold.
 */
public class EntityPlayer extends EntityHuman implements ICrafting {
    public final NetServerHandler netServerHandler = new NetServerHandler();
    public Container activeContainer = new Container();
    public boolean dead;

    public void a(Container container, List items) {
    }

    @Override
    public void a(Container container, int slot, ItemStack itemStack) {
    }

    @Override
    public void a(Container container, int property, int value) {
    }
}
