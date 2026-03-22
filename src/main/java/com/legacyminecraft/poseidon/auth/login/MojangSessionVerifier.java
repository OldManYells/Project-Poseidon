package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.util.SessionAPI;

/**
 * Canonical Mojang session verification flow used by legacy login wrappers.
 */
public final class MojangSessionVerifier {
    private static final MojangSessionVerifier INSTANCE = new MojangSessionVerifier();

    private MojangSessionVerifier() {
    }

    public static MojangSessionVerifier getInstance() {
        return INSTANCE;
    }

    public void verifyUserSession(String requestedUsername, String serverId, String remoteAddress, LoginProcessCallbacks loginCallbacks) {
        SessionAPI.hasJoined(requestedUsername, serverId, remoteAddress, (int responseCode, String returnedUsername, String returnedUUID, String returnedIp) -> {
            boolean checkIP = returnedIp == "127.0.0.1" || returnedIp == "localhost";

            if (responseCode != -1 && responseCode != 204) {
                if (returnedUsername.equalsIgnoreCase(requestedUsername)) {
                    if (checkIP) {
                        if (returnedIp == remoteAddress) {
                            loginCallbacks.userMojangSessionVerified();
                        }
                    } else {
                        loginCallbacks.userMojangSessionVerified();
                    }
                } else {
                    loginCallbacks.cancelLoginProcess("Failed to verify username!");
                }
            } else {
                loginCallbacks.cancelLoginProcess("Failed to verify username!");
            }
        });
    }
}
