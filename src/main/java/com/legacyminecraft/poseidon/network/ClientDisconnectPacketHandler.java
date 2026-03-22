package com.legacyminecraft.poseidon.network;

import net.minecraft.server.NetworkManager;

/**
 * Canonical handler for client-initiated disconnect packets.
 */
public final class ClientDisconnectPacketHandler {
    private static final ClientDisconnectPacketHandler INSTANCE = new ClientDisconnectPacketHandler();
    private static final String DISCONNECT_REASON = "disconnect.quitting";

    private ClientDisconnectPacketHandler() {
    }

    public static ClientDisconnectPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleClientDisconnect(NetworkManager networkManager) {
        networkManager.a(DISCONNECT_REASON, new Object[0]);
    }

    public String getDisconnectReasonKey() {
        return DISCONNECT_REASON;
    }
}
