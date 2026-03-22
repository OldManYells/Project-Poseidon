package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.ServerConfigurationManager;

/**
 * Canonical behaviour for CraftPlayer banlist/whitelist membership policy.
 */
public final class PlayerModerationListBehaviour {
    private static final PlayerModerationListBehaviour INSTANCE = new PlayerModerationListBehaviour();

    private PlayerModerationListBehaviour() {
    }

    public static PlayerModerationListBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isBanned(ServerConfigurationManager configurationManager, String playerName) {
        return configurationManager.banByName.contains(normalizePlayerName(playerName));
    }

    public void setBanned(ServerConfigurationManager configurationManager, String playerName, boolean banned) {
        String normalizedPlayerName = normalizePlayerName(playerName);
        if (banned) {
            configurationManager.a(normalizedPlayerName);
        } else {
            configurationManager.b(normalizedPlayerName);
        }
    }

    public boolean isWhitelisted(ServerConfigurationManager configurationManager, String playerName) {
        return configurationManager.e().contains(normalizePlayerName(playerName));
    }

    public void setWhitelisted(ServerConfigurationManager configurationManager, String playerName,
                               boolean whitelisted) {
        String normalizedPlayerName = normalizePlayerName(playerName);
        if (whitelisted) {
            configurationManager.k(normalizedPlayerName);
        } else {
            configurationManager.l(normalizedPlayerName);
        }
    }

    private String normalizePlayerName(String playerName) {
        return playerName.toLowerCase();
    }
}
