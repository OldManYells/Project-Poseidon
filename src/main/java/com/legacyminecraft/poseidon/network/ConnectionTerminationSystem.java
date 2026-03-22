package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.Packet255KickDisconnect;
import org.bukkit.Server;

/**
 * Canonical coordinator for graceful player disconnect termination flow.
 */
public final class ConnectionTerminationSystem {
    private static final ConnectionTerminationSystem INSTANCE = new ConnectionTerminationSystem();
    private final ConnectionKickProcessor connectionKickProcessor = ConnectionKickProcessor.getInstance();

    private ConnectionTerminationSystem() {
    }

    public static ConnectionTerminationSystem getInstance() {
        return INSTANCE;
    }

    public boolean terminate(
            boolean alreadyDisconnected,
            Server server,
            EntityPlayer player,
            String reason,
            String leaveMessageTemplate,
            PacketSender packetSender,
            NetworkManager networkManager,
            MinecraftServer minecraftServer
    ) {
        if (shouldSkipDisconnect(alreadyDisconnected)) {
            return true;
        }

        ConnectionKickProcessor.KickDecision kickDecision =
                connectionKickProcessor.processKick(server, player, reason, leaveMessageTemplate);
        if (kickDecision.isCancelled()) {
            return false;
        }

        player.B();
        packetSender.sendPacket(createKickPacket(kickDecision.getReason()));
        networkManager.d();

        if (shouldBroadcastLeaveMessage(kickDecision.getLeaveMessage())) {
            minecraftServer.serverConfigurationManager.sendAll(new Packet3Chat(kickDecision.getLeaveMessage()));
        }

        minecraftServer.serverConfigurationManager.disconnect(player);
        return true;
    }

    public boolean shouldSkipDisconnect(boolean alreadyDisconnected) {
        return alreadyDisconnected;
    }

    public boolean shouldBroadcastLeaveMessage(String leaveMessage) {
        return leaveMessage != null;
    }

    public Packet255KickDisconnect createKickPacket(String reason) {
        return new Packet255KickDisconnect(reason);
    }

    public interface PacketSender {
        void sendPacket(Packet packet);
    }
}
