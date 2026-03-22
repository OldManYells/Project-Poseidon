package com.legacyminecraft.poseidon.auth.login;

import java.util.UUID;

/**
 * Shared login callbacks used by canonical auth components and legacy wrappers.
 */
public interface LoginProcessCallbacks {
    void userUUIDReceived(UUID uuid, boolean onlineMode);

    void userMojangSessionVerified();

    void cancelLoginProcess(String message);
}
