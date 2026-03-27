package com.legacyminecraft.compat.bukkit;


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

    public boolean isBanned(Object configurationManager, String playerName) {
        Object banByName = BridgeReflection.getField(configurationManager, "banByName");
        return ((java.util.Set) banByName).contains(normalizePlayerName(playerName));
    }

    public void setBanned(Object configurationManager, String playerName, boolean banned) {
        String normalizedPlayerName = normalizePlayerName(playerName);
        if (banned) {
            BridgeReflection.invoke(configurationManager, "a", normalizedPlayerName);
        } else {
            BridgeReflection.invoke(configurationManager, "b", normalizedPlayerName);
        }
    }

    public boolean isWhitelisted(Object configurationManager, String playerName) {
        Object whitelist = BridgeReflection.invoke(configurationManager, "e");
        return ((java.util.Set) whitelist).contains(normalizePlayerName(playerName));
    }

    public void setWhitelisted(Object configurationManager, String playerName,
                               boolean whitelisted) {
        String normalizedPlayerName = normalizePlayerName(playerName);
        if (whitelisted) {
            BridgeReflection.invoke(configurationManager, "k", normalizedPlayerName);
        } else {
            BridgeReflection.invoke(configurationManager, "l", normalizedPlayerName);
        }
    }

    private String normalizePlayerName(String playerName) {
        return playerName.toLowerCase();
    }
}
