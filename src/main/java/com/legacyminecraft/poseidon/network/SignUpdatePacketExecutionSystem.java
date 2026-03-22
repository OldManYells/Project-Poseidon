package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet130UpdateSign;

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

    public void execute(boolean playerDead, Packet130UpdateSign packet130updateSign, SignUpdateActions signUpdateActions) {
        if (playerDead) {
            return;
        }
        signUpdateActions.process(packet130updateSign);
    }

    public interface SignUpdateActions {
        void process(Packet130UpdateSign packet130updateSign);
    }
}
