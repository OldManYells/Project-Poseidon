package com.legacyminecraft.poseidon.inventory;

import java.util.List;

/**
 * Inventory-local window-items packet alias.
 */
public class Packet104WindowItems extends com.legacyminecraft.compat.bukkit.Packet104WindowItems {
    public Packet104WindowItems(int windowId, List items) {
        super(windowId, items);
    }
}
