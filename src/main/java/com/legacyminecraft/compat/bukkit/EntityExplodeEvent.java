package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical compat explode-event scaffold.
 */
public class EntityExplodeEvent {
    private final List<Object> blockList;
    private boolean cancelled;
    private float yield;

    public EntityExplodeEvent(Object entity, Location location, List<?> blockList) {
        this.blockList = new ArrayList<Object>(blockList == null ? 0 : blockList.size());
        if (blockList != null) {
            this.blockList.addAll(blockList);
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public float getYield() {
        return yield;
    }

    public List<Object> blockList() {
        return blockList;
    }
}
