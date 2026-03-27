package com.legacyminecraft.poseidon.auth.login;

/**
 * Auth-login local PlayerLoginEvent alias.
 */
public class PlayerLoginEvent extends com.legacyminecraft.compat.bukkit.PlayerLoginEvent {
    public PlayerLoginEvent(Player player, Object addressSource) {
        super(player, addressSource);
    }
}

