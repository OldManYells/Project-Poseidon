package com.legacyminecraft.poseidon.network;


/**
 * Canonical policy decisions shared by packet event bridge services.
 */
public final class PacketEventPolicy {
    private static final PacketEventPolicy INSTANCE = new PacketEventPolicy();

    private PacketEventPolicy() {
    }

    public static PacketEventPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isIncomingAllowed(boolean cancelled) {
        return !cancelled;
    }

    public boolean shouldDropOutgoingPacket(Packet packet) {
        return packet == null;
    }

    public boolean shouldBypassOutgoingEventDispatch(boolean firePacketEvents) {
        return !firePacketEvents;
    }
}
