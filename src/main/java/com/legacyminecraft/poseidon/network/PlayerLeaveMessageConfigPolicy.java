package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for player leave-message templates.
 */
public final class PlayerLeaveMessageConfigPolicy {
    private static final PlayerLeaveMessageConfigPolicy INSTANCE = new PlayerLeaveMessageConfigPolicy();
    private static final String PLAYER_LEAVE_MESSAGE_KEY = "message.player.leave";

    private PlayerLeaveMessageConfigPolicy() {
    }

    public static PlayerLeaveMessageConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String playerLeaveMessageKey() {
        return PLAYER_LEAVE_MESSAGE_KEY;
    }
}
