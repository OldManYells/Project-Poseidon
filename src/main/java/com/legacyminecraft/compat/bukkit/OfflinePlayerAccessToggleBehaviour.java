package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for offline-player ban/whitelist toggle orchestration.
 */
public final class OfflinePlayerAccessToggleBehaviour {
    private static final OfflinePlayerAccessToggleBehaviour INSTANCE = new OfflinePlayerAccessToggleBehaviour();
    private static final OfflinePlayerAccessBehaviour OFFLINE_PLAYER_ACCESS_BEHAVIOUR =
            OfflinePlayerAccessBehaviour.getInstance();

    private OfflinePlayerAccessToggleBehaviour() {
    }

    public static OfflinePlayerAccessToggleBehaviour getInstance() {
        return INSTANCE;
    }

    public void setBanned(CraftServer server, String playerName, boolean value) {
        OFFLINE_PLAYER_ACCESS_BEHAVIOUR.setBanned(server, playerName, value);
    }

    public void setWhitelisted(CraftServer server, String playerName, boolean value) {
        OFFLINE_PLAYER_ACCESS_BEHAVIOUR.setWhitelisted(server, playerName, value);
    }
}
