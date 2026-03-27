package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for generic internal server error disconnect messages.
 */
public final class NetworkInternalServerErrorPolicy {
    private static final NetworkInternalServerErrorPolicy INSTANCE = new NetworkInternalServerErrorPolicy();
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";

    private NetworkInternalServerErrorPolicy() {
    }

    public static NetworkInternalServerErrorPolicy getInstance() {
        return INSTANCE;
    }

    public String internalServerErrorMessage() {
        return INTERNAL_SERVER_ERROR_MESSAGE;
    }
}
