package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.api.network.ConnectionType;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;
import org.bukkit.ChatColor;

import java.net.InetSocketAddress;

import static com.legacyminecraft.poseidon.util.Release2Beta.deserializeAddress;

/**
 * Canonical proxy/IP-forwarding policy for login requests.
 */
public final class LoginProxySupport {
    private LoginProxySupport() {
    }

    public static ProxyHandlingResult handleProxy(NetLoginHandler loginHandler, Packet1Login loginPacket) {
        ConnectionType connectionType = resolveConnectionType(loginPacket.d);
        int rawConnectionType = loginPacket.d;
        boolean usingReleaseToBeta = false;

        if ((Boolean) PoseidonConfig.getInstance().getConfigOption("settings.bungeecord.bungee-mode.enable")
                && !connectionType.equals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING)
                && !connectionType.equals(ConnectionType.BUNGEECORD_ONLINE_MODE_IP_FORWARDING)) {
            NetLoginHandler.a.info(loginPacket.name + " is not using BungeeCord, kicking the player.");
            loginHandler.disconnect((String) PoseidonConfig.getInstance().getConfigOption("settings.bungeecord.bungee-mode.kick-message"));
            return ProxyHandlingResult.rejected(connectionType, rawConnectionType, usingReleaseToBeta);
        }

        if (connectionType.equals(ConnectionType.RELEASE2BETA_OFFLINE_MODE_IP_FORWARDING)
                || connectionType.equals(ConnectionType.RELEASE2BETA_ONLINE_MODE_IP_FORWARDING)
                || connectionType.equals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING)
                || connectionType.equals(ConnectionType.BUNGEECORD_ONLINE_MODE_IP_FORWARDING)) {
            if ((Boolean) PoseidonConfig.getInstance().getConfigOption("settings.release2beta.enable-ip-pass-through")) {
                if (loginHandler.getSocket().getInetAddress().getHostAddress()
                        .equalsIgnoreCase(String.valueOf(PoseidonConfig.getInstance().getConfigOption("settings.release2beta.proxy-ip", "127.0.0.1")))) {
                    InetSocketAddress address = deserializeAddress(loginPacket.c);
                    NetLoginHandler.a.info(loginPacket.name + " has been detected using Release2Beta, using the IP passed through: " + address.getAddress().getHostAddress());
                    loginHandler.networkManager.setSocketAddress(address);
                    usingReleaseToBeta = true;
                } else {
                    NetLoginHandler.a.info(loginPacket.name + " is attempting to use a unauthorized Release2Beta server, kicking the player.");
                    loginHandler.disconnect(ChatColor.RED + "The Release2Beta server you are connecting through is unauthorized.");
                    return ProxyHandlingResult.rejected(connectionType, rawConnectionType, usingReleaseToBeta);
                }
            } else {
                NetLoginHandler.a.info(loginPacket.name + " is trying to connect through R2B with IP Forwarding enabled, however, it is disabled in Poseidon. Kicking player!");
                loginHandler.disconnect(ChatColor.RED + "IP Forwarding is disabled in Poseidon. Please disable in Release2Beta.");
                return ProxyHandlingResult.rejected(connectionType, rawConnectionType, usingReleaseToBeta);
            }
        }

        return ProxyHandlingResult.accepted(connectionType, rawConnectionType, usingReleaseToBeta);
    }

    public static ConnectionType resolveConnectionType(byte rawConnectionType) {
        if (rawConnectionType == (byte) -999 || rawConnectionType == (byte) 25) {
            return ConnectionType.RELEASE2BETA_OFFLINE_MODE_IP_FORWARDING;
        } else if (rawConnectionType == (byte) 26) {
            return ConnectionType.RELEASE2BETA_ONLINE_MODE_IP_FORWARDING;
        } else if (rawConnectionType == (byte) 1) {
            return ConnectionType.RELEASE2BETA;
        } else if (rawConnectionType == (byte) 2) {
            return ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING;
        }
        return ConnectionType.NORMAL;
    }

    public static final class ProxyHandlingResult {
        private final boolean accepted;
        private final ConnectionType connectionType;
        private final int rawConnectionType;
        private final boolean usingReleaseToBeta;

        private ProxyHandlingResult(boolean accepted, ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
            this.accepted = accepted;
            this.connectionType = connectionType;
            this.rawConnectionType = rawConnectionType;
            this.usingReleaseToBeta = usingReleaseToBeta;
        }

        public static ProxyHandlingResult accepted(ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
            return new ProxyHandlingResult(true, connectionType, rawConnectionType, usingReleaseToBeta);
        }

        public static ProxyHandlingResult rejected(ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
            return new ProxyHandlingResult(false, connectionType, rawConnectionType, usingReleaseToBeta);
        }

        public boolean isAccepted() {
            return accepted;
        }

        public ConnectionType getConnectionType() {
            return connectionType;
        }

        public int getRawConnectionType() {
            return rawConnectionType;
        }

        public boolean isUsingReleaseToBeta() {
            return usingReleaseToBeta;
        }
    }
}
