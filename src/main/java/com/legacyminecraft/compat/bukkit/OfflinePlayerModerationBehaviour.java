package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for offline-player moderation state reads and toggles.
 */
public final class OfflinePlayerModerationBehaviour {
    private static final OfflinePlayerModerationBehaviour INSTANCE = new OfflinePlayerModerationBehaviour();
    private static final OfflinePlayerAccessReadPolicy OFFLINE_PLAYER_ACCESS_READ_POLICY =
            OfflinePlayerAccessReadPolicy.getInstance();
    private static final OfflinePlayerOperatorToggleBehaviour OFFLINE_PLAYER_OPERATOR_TOGGLE_BEHAVIOUR =
            OfflinePlayerOperatorToggleBehaviour.getInstance();
    private static final OfflinePlayerAccessToggleBehaviour OFFLINE_PLAYER_ACCESS_TOGGLE_BEHAVIOUR =
            OfflinePlayerAccessToggleBehaviour.getInstance();
    private static final OfflinePlayerNameResolutionBehaviour OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR =
            OfflinePlayerNameResolutionBehaviour.getInstance();

    private OfflinePlayerModerationBehaviour() {
    }

    public static OfflinePlayerModerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOp(CraftServer server, String playerName, String resolvedName) {
        return OFFLINE_PLAYER_ACCESS_READ_POLICY.isOperator(
                server,
                OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR.resolveForOperatorRead(playerName, resolvedName)
        );
    }

    public void setOp(CraftServer server, String playerName, String resolvedName, boolean value) {
        OFFLINE_PLAYER_OPERATOR_TOGGLE_BEHAVIOUR.setOp(
                server,
                OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR.resolveForOperatorToggle(playerName, resolvedName),
                value
        );
    }

    public boolean isBanned(CraftServer server, String playerName, String resolvedName) {
        return OFFLINE_PLAYER_ACCESS_READ_POLICY.isBanned(
                server,
                OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR.resolveForBanRead(playerName, resolvedName)
        );
    }

    public void setBanned(CraftServer server, String playerName, String resolvedName, boolean value) {
        OFFLINE_PLAYER_ACCESS_TOGGLE_BEHAVIOUR.setBanned(
                server,
                OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR.resolveForBanToggle(playerName, resolvedName),
                value
        );
    }

    public boolean isWhitelisted(CraftServer server, String playerName, String resolvedName) {
        return OFFLINE_PLAYER_ACCESS_READ_POLICY.isWhitelisted(
                server,
                OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR.resolveForWhitelistRead(playerName, resolvedName)
        );
    }

    public void setWhitelisted(CraftServer server, String playerName, String resolvedName, boolean value) {
        OFFLINE_PLAYER_ACCESS_TOGGLE_BEHAVIOUR.setWhitelisted(
                server,
                OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR.resolveForWhitelistToggle(playerName, resolvedName),
                value
        );
    }
}
