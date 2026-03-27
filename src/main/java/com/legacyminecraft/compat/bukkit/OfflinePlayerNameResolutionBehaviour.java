package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for offline-player name resolution in access policy calls.
 */
public final class OfflinePlayerNameResolutionBehaviour {
    private static final OfflinePlayerNameResolutionBehaviour INSTANCE = new OfflinePlayerNameResolutionBehaviour();

    private OfflinePlayerNameResolutionBehaviour() {
    }

    public static OfflinePlayerNameResolutionBehaviour getInstance() {
        return INSTANCE;
    }

    public String resolveForOperatorRead(String name, String apiName) {
        return apiName;
    }

    public String resolveForOperatorToggle(String name, String apiName) {
        return apiName;
    }

    public String resolveForBanRead(String name, String apiName) {
        return name;
    }

    public String resolveForBanToggle(String name, String apiName) {
        return name;
    }

    public String resolveForWhitelistRead(String name, String apiName) {
        return name;
    }

    public String resolveForWhitelistToggle(String name, String apiName) {
        return name;
    }
}
