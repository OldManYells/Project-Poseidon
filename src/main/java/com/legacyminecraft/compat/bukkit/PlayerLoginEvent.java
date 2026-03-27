package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat player-login event scaffold.
 */
public class PlayerLoginEvent extends Event {
    public enum Result {
        ALLOWED,
        KICK_BANNED,
        KICK_BANNED_IP,
        KICK_WHITELIST,
        KICK_FULL,
        KICK_OTHER
    }

    private final Player player;
    private final Object addressSource;
    private Result result = Result.ALLOWED;
    private String kickMessage = "";

    public PlayerLoginEvent(Player player, Object addressSource) {
        this.player = player;
        this.addressSource = addressSource;
    }

    public Player getPlayer() {
        return player;
    }

    public Object getAddressSource() {
        return addressSource;
    }

    public Result getResult() {
        return result;
    }

    public void disallow(Result result, String message) {
        this.result = result;
        this.kickMessage = message;
    }

    public String getKickMessage() {
        return kickMessage;
    }
}

