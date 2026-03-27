package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for join/leave debug logging controls.
 */
public final class ConnectionLossDebugConfigPolicy {
    private static final ConnectionLossDebugConfigPolicy INSTANCE = new ConnectionLossDebugConfigPolicy();
    private static final String REMOVE_JOIN_LEAVE_DEBUG_KEY = "settings.remove-join-leave-debug";
    private static final boolean REMOVE_JOIN_LEAVE_DEBUG_DEFAULT = true;

    private ConnectionLossDebugConfigPolicy() {
    }

    public static ConnectionLossDebugConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String removeJoinLeaveDebugKey() {
        return REMOVE_JOIN_LEAVE_DEBUG_KEY;
    }

    public boolean removeJoinLeaveDebugDefault() {
        return REMOVE_JOIN_LEAVE_DEBUG_DEFAULT;
    }
}
