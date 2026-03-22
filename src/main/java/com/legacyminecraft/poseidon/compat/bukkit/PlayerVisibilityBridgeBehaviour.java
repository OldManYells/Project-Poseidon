package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.EntityTrackerEntry;
import net.minecraft.server.WorldServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * Canonical behaviour for CraftPlayer hide/show/canSee tracking and tracker-entry bridge policy.
 */
public final class PlayerVisibilityBridgeBehaviour {
    private static final PlayerVisibilityBridgeBehaviour INSTANCE = new PlayerVisibilityBridgeBehaviour();

    private PlayerVisibilityBridgeBehaviour() {
    }

    public static PlayerVisibilityBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void hidePlayer(Set<UUID> hiddenPlayers, Entity observerEntity, EntityPlayer observerHandle, Player player) {
        hiddenPlayers.add(player.getUniqueId());

        EntityTracker tracker = ((WorldServer) observerEntity.world).tracker;
        EntityPlayer hiddenPlayer = ((CraftPlayer) player).getHandle();
        EntityTrackerEntry trackerEntry = (EntityTrackerEntry) tracker.b.a(hiddenPlayer.id);
        if (trackerEntry != null) {
            trackerEntry.c(observerHandle);
        }
    }

    public void showPlayer(Set<UUID> hiddenPlayers, Entity observerEntity, EntityPlayer observerHandle, Player player) {
        hiddenPlayers.remove(player.getUniqueId());

        EntityTracker tracker = ((WorldServer) observerEntity.world).tracker;
        EntityPlayer shownPlayer = ((CraftPlayer) player).getHandle();
        EntityTrackerEntry trackerEntry = (EntityTrackerEntry) tracker.b.a(shownPlayer.id);
        if (trackerEntry != null && !trackerEntry.trackedPlayers.contains(observerHandle)) {
            trackerEntry.b(observerHandle);
        }
    }

    public boolean canSee(Set<UUID> hiddenPlayers, Player player) {
        return !hiddenPlayers.contains(player.getUniqueId());
    }
}
