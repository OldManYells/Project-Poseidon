package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer config/property accessors.
 */
public final class CraftServerConfigBehaviour {
    private static final CraftServerConfigBehaviour INSTANCE = new CraftServerConfigBehaviour();

    private CraftServerConfigBehaviour() {
    }

    public static CraftServerConfigBehaviour getInstance() {
        return INSTANCE;
    }

    public int getPort(MinecraftServer console) {
        return this.getConfigInt(console, "server-port", 25565);
    }

    public int getViewDistance(MinecraftServer console) {
        return this.getConfigInt(console, "view-distance", 10);
    }

    public String getIp(MinecraftServer console) {
        return this.getConfigString(console, "server-ip", "");
    }

    public String getServerName(MinecraftServer console) {
        return this.getConfigString(console, "server-name", "Unknown Server");
    }

    public String getServerId(MinecraftServer console) {
        return this.getConfigString(console, "server-id", "unnamed");
    }

    public boolean getAllowNether(MinecraftServer console) {
        return this.getConfigBoolean(console, "allow-nether", true);
    }

    public boolean hasWhitelist(MinecraftServer console) {
        return this.getConfigBoolean(console, "white-list", false);
    }

    public boolean getOnlineMode(MinecraftServer console) {
        return console.onlineMode;
    }

    public boolean getAllowFlight(MinecraftServer console) {
        return console.allowFlight;
    }

    private String getConfigString(MinecraftServer console, String variable, String defaultValue) {
        return this.getConfig(console).getString(variable, defaultValue);
    }

    private int getConfigInt(MinecraftServer console, String variable, int defaultValue) {
        return this.getConfig(console).getInt(variable, defaultValue);
    }

    private boolean getConfigBoolean(MinecraftServer console, String variable, boolean defaultValue) {
        return this.getConfig(console).getBoolean(variable, defaultValue);
    }

    private PropertyManager getConfig(MinecraftServer console) {
        return console.propertyManager;
    }
}
