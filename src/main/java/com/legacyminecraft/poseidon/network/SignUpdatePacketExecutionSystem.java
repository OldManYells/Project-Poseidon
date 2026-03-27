package com.legacyminecraft.poseidon.network;


/**
 * Canonical execution flow for sign update packet handling preconditions.
 */
public final class SignUpdatePacketExecutionSystem {
    private static final SignUpdatePacketExecutionSystem INSTANCE = new SignUpdatePacketExecutionSystem();

    private SignUpdatePacketExecutionSystem() {
    }

    public static SignUpdatePacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(boolean playerDead, Object packet130updateSign, SignUpdateActions signUpdateActions) {
        if (playerDead) {
            return;
        }
        signUpdateActions.process(packet130updateSign);
    }

    public interface SignUpdateActions {
        void process(Object packet130updateSign);
    }
}
