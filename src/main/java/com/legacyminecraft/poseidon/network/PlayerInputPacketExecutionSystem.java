package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet27;

/**
 * Canonical execution flow for packet 27 player input forwarding.
 */
public final class PlayerInputPacketExecutionSystem {
    private static final PlayerInputPacketExecutionSystem INSTANCE = new PlayerInputPacketExecutionSystem();

    private PlayerInputPacketExecutionSystem() {
    }

    public static PlayerInputPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(Packet27 packet27, InputActions inputActions) {
        inputActions.applyInput(
                packet27.c(),
                packet27.e(),
                packet27.g(),
                packet27.h(),
                packet27.d(),
                packet27.f()
        );
    }

    public interface InputActions {
        void applyInput(float primaryX, float primaryY, boolean primaryFlag, boolean secondaryFlag, float secondaryX, float secondaryY);
    }
}
