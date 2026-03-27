package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for CraftServer metadata getters.
 */
public final class CraftServerMetadataBehaviour {
    private static final CraftServerMetadataBehaviour INSTANCE = new CraftServerMetadataBehaviour();

    private CraftServerMetadataBehaviour() {
    }

    public static CraftServerMetadataBehaviour getInstance() {
        return INSTANCE;
    }

    public String getGameVersion(String gameVersion) {
        return gameVersion;
    }

    public String getName(String serverName) {
        return serverName;
    }

    public String getPoseidonVersion(String serverVersion) {
        return serverVersion;
    }

    public String getPoseidonReleaseType(String releaseType) {
        return releaseType;
    }

    public String getServerEnvironment(String serverEnvironment) {
        return serverEnvironment;
    }

    public String getVersion(String serverVersion, String protocolVersion) {
        return serverVersion + " (MC: " + protocolVersion + ")";
    }

    public String toString(String serverName, String serverVersion, String protocolVersion) {
        return "CraftServer{" + "serverName=" + serverName + ",serverVersion=" + serverVersion + ",protocolVersion=" + protocolVersion + '}';
    }
}
