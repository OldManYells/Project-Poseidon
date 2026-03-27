package com.legacyminecraft.compat.bukkit;

import java.util.HashSet;
import java.util.Set;

/**
 * Canonical compat entity-tracker entry scaffold.
 */
public class EntityTrackerEntry {
    public final Set<EntityPlayer> trackedPlayers = new HashSet<EntityPlayer>();

    public void c(EntityPlayer player) {
        trackedPlayers.remove(player);
    }

    public void b(EntityPlayer player) {
        trackedPlayers.add(player);
    }
}
