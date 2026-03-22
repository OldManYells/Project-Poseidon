package com.projectposeidon.api;

import java.util.UUID;

/**
 * @deprecated Use {@link com.legacyminecraft.poseidon.api.uuid.PoseidonUUID}.
 */
@Deprecated
public final class PoseidonUUID {

    private PoseidonUUID() {

    }

    /**
     * @param username Username of a player who has connected
     * @return A Mojang UUID if known, otherwise null
     */
    public static UUID getPlayerMojangUUID(String username) {
        return com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerMojangUUID(username);
    }

    /**
     * @param username Username of a player who has connected
     * @return A Mojang UUID if known, otherwise a offline uuid
     */
    public static UUID getPlayerGracefulUUID(String username) {
        return com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerGracefulUUID(username);
    }

    /**
     * Get a UUID of a player IF they have joined before.
     *
     * @param username   Username of a player who has connected
     * @param onlineUUID Search for online or offline UUIDs?
     * @return Returns a UUID if known in cache, otherwise null
     */
    public static UUID getPlayerUUIDFromCache(String username, boolean onlineUUID) {
        return com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerUUIDFromCache(username, onlineUUID);
    }

    /**
     * @param username Username of a player
     * @return A offline UUID for a player
     */
    public static UUID getPlayerOfflineUUID(String username) {
        return com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerOfflineUUID(username);
    }

    /**
     * @param username Username of a player
     * @return A UUIDType enum.
     */
    public static UUIDType getPlayerUUIDCacheStatus(String username) {
        return UUIDType.fromCanonical(com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerUUIDCacheStatus(username));
    }

    /**
     * @param uuid UUID for a player
     * @return A corresponding username if known, otherwise null
     */
    public static String getPlayerUsernameFromUUID(UUID uuid) {
        return com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerUsernameFromUUID(uuid);
    }


}
