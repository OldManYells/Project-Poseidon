package com.legacyminecraft.poseidon.network;


/**
 * Canonical forwarding behaviour for legacy typed NetHandler packet callbacks.
 */
public final class NetHandlerPacketForwardingBehaviour {
    private static final NetHandlerPacketForwardingBehaviour INSTANCE = new NetHandlerPacketForwardingBehaviour();

    private NetHandlerPacketForwardingBehaviour() {
    }

    public static NetHandlerPacketForwardingBehaviour getInstance() {
        return INSTANCE;
    }

    public void forwardToGeneric(NetHandler netHandler, Packet packet) {
        netHandler.a(packet);
    }
}
