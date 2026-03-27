package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge for refreshing operator permission state after list changes.
 */
public final class OperatorPermissionRefreshBridgeBehaviour {
    private static final OperatorPermissionRefreshBridgeBehaviour INSTANCE = new OperatorPermissionRefreshBridgeBehaviour();

    private OperatorPermissionRefreshBridgeBehaviour() {
    }

    public static OperatorPermissionRefreshBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void refreshPermissionsIfOnline(Server server, String playerName) {
        if (server == null) {
            return;
        }
        Player player = server.getPlayer(playerName);
        if (player != null) {
            player.recalculatePermissions();
        }
    }
}
