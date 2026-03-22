package com.legacyminecraft.poseidon.network;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

import java.util.List;

/**
 * Canonical inbound queue processing loop for NetworkManager.
 */
public final class InboundPacketProcessingSystem {
    private static final InboundPacketProcessingSystem INSTANCE = new InboundPacketProcessingSystem();
    private final IncomingPlayerPacketDispatchSystem incomingPlayerPacketDispatchSystem = IncomingPlayerPacketDispatchSystem.getInstance();

    private InboundPacketProcessingSystem() {
    }

    public static InboundPacketProcessingSystem getInstance() {
        return INSTANCE;
    }

    public int processInboundQueue(List inboundQueue, int processingBudget, NetHandler handler, boolean firePacketEvents, String username) {
        int processed = 0;
        int remaining = processingBudget;
        while (!inboundQueue.isEmpty() && remaining-- >= 0) {
            Packet packet = (Packet) inboundQueue.remove(0);
            IncomingPlayerPacketDispatchSystem.DispatchDecision dispatchDecision =
                    incomingPlayerPacketDispatchSystem.resolve(firePacketEvents, handler, username, packet);
            if (dispatchDecision.shouldDispatch()) {
                dispatchDecision.getPacket().a(handler);
            }
            processed++;
        }
        return processed;
    }
}
