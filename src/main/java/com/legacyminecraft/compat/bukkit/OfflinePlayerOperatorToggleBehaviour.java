package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for offline-player operator toggle orchestration.
 */
public final class OfflinePlayerOperatorToggleBehaviour {
    private static final OfflinePlayerOperatorToggleBehaviour INSTANCE = new OfflinePlayerOperatorToggleBehaviour();
    private static final OfflinePlayerAccessBehaviour OFFLINE_PLAYER_ACCESS_BEHAVIOUR =
            OfflinePlayerAccessBehaviour.getInstance();

    private OfflinePlayerOperatorToggleBehaviour() {
    }

    public static OfflinePlayerOperatorToggleBehaviour getInstance() {
        return INSTANCE;
    }

    public void setOp(CraftServer server, String playerName, boolean value) {
        if (value == OFFLINE_PLAYER_ACCESS_BEHAVIOUR.isOperator(server, playerName)) {
            return;
        }
        OFFLINE_PLAYER_ACCESS_BEHAVIOUR.setOperator(server, playerName, value);
    }
}
