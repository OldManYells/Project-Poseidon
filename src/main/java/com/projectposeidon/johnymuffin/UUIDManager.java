package com.projectposeidon.johnymuffin;

import java.util.UUID;

/**
 * @deprecated Use {@link com.legacyminecraft.poseidon.auth.uuid.UUIDManager}.
 */
@Deprecated
public final class UUIDManager {
    private final com.legacyminecraft.poseidon.auth.uuid.UUIDManager delegate;

    private static final class Holder {
        private static final UUIDManager INSTANCE = new UUIDManager();
    }

    private UUIDManager() {
        this.delegate = com.legacyminecraft.poseidon.auth.uuid.UUIDManager.getInstance();
    }

    public static UUIDManager getInstance() {
        return Holder.INSTANCE;
    }

    public static UUID generateOfflineUUID(String username) {
        return com.legacyminecraft.poseidon.auth.uuid.UUIDManager.generateOfflineUUID(username);
    }

    public UUID getUUIDGraceful(String username) {
        return delegate.getUUIDGraceful(username);
    }

    public void saveJsonArray() {
        delegate.saveJsonArray();
    }

    public void receivedUUID(String username, UUID uuid, Long expiry, boolean online) {
        delegate.receivedUUID(username, uuid, expiry, online);
    }

    public UUID getUUIDFromUsername(String username) {
        return delegate.getUUIDFromUsername(username);
    }

    public UUID getUUIDFromUsername(String username, boolean online) {
        return delegate.getUUIDFromUsername(username, online);
    }

    public UUID getUUIDFromUsername(String username, boolean online, long afterUnix) {
        return delegate.getUUIDFromUsername(username, online, afterUnix);
    }

    public String getUsernameFromUUID(UUID uuid) {
        return delegate.getUsernameFromUUID(uuid);
    }
}
