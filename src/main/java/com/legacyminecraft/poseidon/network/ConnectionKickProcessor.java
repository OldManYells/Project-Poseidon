package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import org.bukkit.Server;
import org.bukkit.event.player.PlayerKickEvent;

/**
 * Canonical processor for player kick events and leave-message resolution.
 */
public final class ConnectionKickProcessor {
    private static final ConnectionKickProcessor INSTANCE = new ConnectionKickProcessor();

    private ConnectionKickProcessor() {
    }

    public static ConnectionKickProcessor getInstance() {
        return INSTANCE;
    }

    public KickDecision processKick(Server server, EntityPlayer player, String reason, String leaveMessageTemplate) {
        String leaveMessage = buildLeaveMessage(leaveMessageTemplate, player.name);
        PlayerKickEvent event = new PlayerKickEvent(server.getPlayer(player.name), reason, leaveMessage);
        server.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return KickDecision.cancelled();
        }

        return KickDecision.allowed(event.getReason(), event.getLeaveMessage());
    }

    public String buildLeaveMessage(String leaveMessageTemplate, String playerName) {
        return leaveMessageTemplate.replace("%player%", playerName);
    }

    public static final class KickDecision {
        private final boolean cancelled;
        private final String reason;
        private final String leaveMessage;

        private KickDecision(boolean cancelled, String reason, String leaveMessage) {
            this.cancelled = cancelled;
            this.reason = reason;
            this.leaveMessage = leaveMessage;
        }

        public static KickDecision cancelled() {
            return new KickDecision(true, null, null);
        }

        public static KickDecision allowed(String reason, String leaveMessage) {
            return new KickDecision(false, reason, leaveMessage);
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public String getReason() {
            return reason;
        }

        public String getLeaveMessage() {
            return leaveMessage;
        }
    }
}
