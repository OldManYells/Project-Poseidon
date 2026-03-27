package com.legacyminecraft.poseidon.auth.login;


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

    public void execute(Object loginPacket, PendingPacketState pendingPacketState, Runnable execution) {
        pendingPacketState.set(loginPacket);
        try {
            execution.run();
        } finally {
            pendingPacketState.clear();
        }
    }

    public interface PendingPacketState {
        void set(Object loginPacket);

        void clear();
    }
}
