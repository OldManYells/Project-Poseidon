package com.legacyminecraft.poseidon.network;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

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
