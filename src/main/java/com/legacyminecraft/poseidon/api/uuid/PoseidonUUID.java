package com.legacyminecraft.poseidon.api.uuid;

import com.legacyminecraft.poseidon.auth.uuid.UUIDManager;

import java.util.UUID;

public final class PoseidonUUID {
    private PoseidonUUID() {
    }

    public static UUID getPlayerMojangUUID(String username) {
        return UUIDManager.getInstance().getUUIDFromUsername(username, true);
    }

    public static UUID getPlayerGracefulUUID(String username) {
        return UUIDManager.getInstance().getUUIDGraceful(username);
    }

    public static UUID getPlayerUUIDFromCache(String username, boolean onlineUUID) {
        return UUIDManager.getInstance().getUUIDFromUsername(username, onlineUUID);
    }

    public static UUID getPlayerOfflineUUID(String username) {
        return UUIDManager.generateOfflineUUID(username);
    }

    public static UUIDType getPlayerUUIDCacheStatus(String username) {
        if (getPlayerUUIDFromCache(username, true) != null) {
            return UUIDType.ONLINE;
        }
        if (getPlayerUUIDFromCache(username, false) != null) {
            return UUIDType.OFFLINE;
        }
        return UUIDType.UNKNOWN;
    }

    public static String getPlayerUsernameFromUUID(UUID uuid) {
        return UUIDManager.getInstance().getUsernameFromUUID(uuid);
    }
}
