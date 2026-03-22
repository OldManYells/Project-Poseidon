package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.event.PlayerReceivePacketEvent;
import net.minecraft.server.NetHandler;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet;
import org.bukkit.Bukkit;

/**
 * Canonical service for NetworkManager incoming packet dispatch resolution.
 */
public final class IncomingPlayerPacketDispatchSystem {
    private static final IncomingPlayerPacketDispatchSystem INSTANCE = new IncomingPlayerPacketDispatchSystem();

    private IncomingPlayerPacketDispatchSystem() {
    }

    public static IncomingPlayerPacketDispatchSystem getInstance() {
        return INSTANCE;
    }

    public DispatchDecision resolve(boolean firePacketEvents, NetHandler netHandler, String username, Packet packet) {
        if (!firePacketEvents || !(netHandler instanceof NetServerHandler)) {
            return DispatchDecision.dispatch(packet);
        }

        PlayerReceivePacketEvent event = new PlayerReceivePacketEvent(username, packet);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return DispatchDecision.skipDispatch();
        }
        return DispatchDecision.dispatch(event.getPacket());
    }

    public static final class DispatchDecision {
        private final boolean shouldDispatch;
        private final Packet packet;

        private DispatchDecision(boolean shouldDispatch, Packet packet) {
            this.shouldDispatch = shouldDispatch;
            this.packet = packet;
        }

        public static DispatchDecision dispatch(Packet packet) {
            return new DispatchDecision(true, packet);
        }

        public static DispatchDecision skipDispatch() {
            return new DispatchDecision(false, null);
        }

        public boolean shouldDispatch() {
            return shouldDispatch;
        }

        public Packet getPacket() {
            return packet;
        }
    }
}
