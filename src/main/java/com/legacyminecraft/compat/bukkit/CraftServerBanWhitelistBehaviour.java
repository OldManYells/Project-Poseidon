package com.legacyminecraft.compat.bukkit;


import java.util.HashSet;
import java.util.Set;

/**
 * Canonical behaviour for CraftServer ban and whitelist wrapper glue.
 */
public final class CraftServerBanWhitelistBehaviour {
    private static final CraftServerBanWhitelistBehaviour INSTANCE = new CraftServerBanWhitelistBehaviour();

    private CraftServerBanWhitelistBehaviour() {
    }

    public static CraftServerBanWhitelistBehaviour getInstance() {
        return INSTANCE;
    }

    public Set<String> getIPBans(ServerConfigurationManager configurationManager) {
        return new HashSet<String>(configurationManager.banByIP);
    }

    public void banIP(ServerConfigurationManager configurationManager, String address) {
        configurationManager.c(address);
    }

    public void unbanIP(ServerConfigurationManager configurationManager, String address) {
        configurationManager.d(address);
    }

    public Set<OfflinePlayer> getBannedPlayers(CraftServer server) {
        Set<OfflinePlayer> bannedPlayers = new HashSet<OfflinePlayer>();

        for (Object playerName : server.getHandle().banByName) {
            bannedPlayers.add(server.getOfflinePlayer((String) playerName));
        }

        return bannedPlayers;
    }

    public void setWhitelist(MinecraftServer console, ServerConfigurationManager configurationManager, boolean value) {
        configurationManager.o = value;
        console.propertyManager.b("white-list", value);
        console.propertyManager.savePropertiesFile();
    }

    public Set<OfflinePlayer> getWhitelistedPlayers(CraftServer server, ServerConfigurationManager configurationManager) {
        Set<OfflinePlayer> whitelistedPlayers = new HashSet<OfflinePlayer>();

        for (Object playerName : configurationManager.e()) {
            whitelistedPlayers.add(server.getOfflinePlayer((String) playerName));
        }

        return whitelistedPlayers;
    }

    public void reloadWhitelist(ServerConfigurationManager configurationManager) {
        configurationManager.f();
    }
}
