package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer permission-broadcast wrapper glue.
 */
public final class CraftServerPermissionBroadcastBehaviour {
    private static final CraftServerPermissionBroadcastBehaviour INSTANCE =
            new CraftServerPermissionBroadcastBehaviour();

    private CraftServerPermissionBroadcastBehaviour() {
    }

    public static CraftServerPermissionBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public int broadcast(Player[] players, String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }

        return players.length;
    }
}
