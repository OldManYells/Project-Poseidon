package com.legacyminecraft.poseidon.auth.login;

import net.minecraft.server.Packet1Login;

/**
 * Canonical helper for scoped pending login-packet state around delegated execution.
 */
public final class LoginPendingPacketExecutionSystem {
    private static final LoginPendingPacketExecutionSystem INSTANCE = new LoginPendingPacketExecutionSystem();

    private LoginPendingPacketExecutionSystem() {
    }

    public static LoginPendingPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(Packet1Login loginPacket, PendingPacketState pendingPacketState, Runnable execution) {
        pendingPacketState.set(loginPacket);
        try {
            execution.run();
        } finally {
            pendingPacketState.clear();
        }
    }

    public interface PendingPacketState {
        void set(Packet1Login loginPacket);

        void clear();
    }
}
