package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.poseidon.compat.bukkit.InventorySlotBridgeBehaviour;
import net.minecraft.server.Slot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftSlot implements org.bukkit.inventory.Slot {
    private static final InventorySlotBridgeBehaviour INVENTORY_SLOT_BRIDGE_BEHAVIOUR =
            InventorySlotBridgeBehaviour.getInstance();
    private final Slot slot;

    public CraftSlot(Slot slot) {
        this.slot = slot;
    }

    public Inventory getInventory() {
        return INVENTORY_SLOT_BRIDGE_BEHAVIOUR.getInventory(slot);
    }

    public int getIndex() {
        return slot.index;
    }

    public ItemStack getItem() {
        return INVENTORY_SLOT_BRIDGE_BEHAVIOUR.getItem(slot);
    }
}
