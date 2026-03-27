package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer broadcast-message wrapper glue.
 */
public final class CraftServerBroadcastBehaviour {
    private static final CraftServerBroadcastBehaviour INSTANCE = new CraftServerBroadcastBehaviour();

    private CraftServerBroadcastBehaviour() {
    }

    public static CraftServerBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public int broadcastMessage(Player[] onlinePlayers, String message) {
        for (Player player : onlinePlayers) {
            player.sendMessage(message);
        }

        return onlinePlayers.length;
    }
}
