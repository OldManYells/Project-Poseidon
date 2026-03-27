package com.legacyminecraft.poseidon.network;


/**
 * Canonical bridge for packet receive/send event dispatch.
 */
public final class PacketEventBridge {
    private static final PacketEventBridge INSTANCE = new PacketEventBridge();
    private final IncomingPacketEventSystem incomingPacketEventSystem = IncomingPacketEventSystem.getInstance();
    private final OutgoingPacketEventSystem outgoingPacketEventSystem = OutgoingPacketEventSystem.getInstance();

    private PacketEventBridge() {
    }

    public static PacketEventBridge getInstance() {
        return INSTANCE;
    }

    public boolean allowIncomingPacket(Server server, EntityPlayer player, Packet packet) {
        return incomingPacketEventSystem.allowIncomingPacket(server, player, packet);
    }

    public Packet filterOutgoingPacket(boolean firePacketEvents, EntityPlayer player, Packet packet) {
        return outgoingPacketEventSystem.filterOutgoingPacket(firePacketEvents, player, packet);
    }
}
