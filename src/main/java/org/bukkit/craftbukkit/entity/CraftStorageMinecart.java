package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.StorageMinecartInventoryBridgeBehaviour;
import net.minecraft.server.EntityMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.inventory.Inventory;

public class CraftStorageMinecart extends CraftMinecart implements StorageMinecart {
    private static final StorageMinecartInventoryBridgeBehaviour STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR =
            StorageMinecartInventoryBridgeBehaviour.getInstance();
    private CraftInventory inventory;

    public CraftStorageMinecart(CraftServer server, EntityMinecart entity) {
        super(server, entity);
        inventory = STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity);
    }

    public Inventory getInventory() {
        return STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR.toInventory(inventory);
    }

    @Override
    public String toString() {
        return "CraftStorageMinecart{" + "inventory=" + inventory + '}';
    }
}
