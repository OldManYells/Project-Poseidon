package com.legacyminecraft.poseidon.packet;

import com.legacyminecraft.poseidon.packets.ArtificialPacket53BlockChange;

/**
 * Canonical default packet-id registration catalog.
 */
public final class PacketRegistrationBootstrap {
    private static final PacketRegistrationBootstrap INSTANCE = new PacketRegistrationBootstrap();

    private PacketRegistrationBootstrap() {
    }

    public static PacketRegistrationBootstrap getInstance() {
        return INSTANCE;
    }

    public void registerDefaults(PacketProtocol packetProtocolService) {
        // Register only canonical packet classes that are implemented in this package hierarchy.
        packetProtocolService.registerPacket(53, true, false, ArtificialPacket53BlockChange.class);
        packetProtocolService.registerPacket(131, true, false, Packet131.class);
    }
}
