package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for login admission and player-join messaging.
 */
public final class ServerAccessMessageConfigPolicy {
    private static final ServerAccessMessageConfigPolicy INSTANCE = new ServerAccessMessageConfigPolicy();
    private static final String KICK_BANNED_KEY = "message.kick.banned";
    private static final String KICK_IP_BANNED_KEY = "message.kick.ip-banned";
    private static final String KICK_NOT_WHITELISTED_KEY = "message.kick.not-whitelisted";
    private static final String KICK_FULL_KEY = "message.kick.full";
    private static final String PLAYER_JOIN_KEY = "message.player.join";

    private ServerAccessMessageConfigPolicy() {
    }

    public static ServerAccessMessageConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String kickBannedKey() {
        return KICK_BANNED_KEY;
    }

    public String kickIpBannedKey() {
        return KICK_IP_BANNED_KEY;
    }

    public String kickNotWhitelistedKey() {
        return KICK_NOT_WHITELISTED_KEY;
    }

    public String kickFullKey() {
        return KICK_FULL_KEY;
    }

    public String playerJoinKey() {
        return PLAYER_JOIN_KEY;
    }
}
