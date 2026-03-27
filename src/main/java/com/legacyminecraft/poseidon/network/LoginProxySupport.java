package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.api.network.ConnectionType;

import java.net.InetSocketAddress;

import static com.legacyminecraft.poseidon.util.Release2Beta.deserializeAddress;

/**
 * Canonical proxy/IP-forwarding policy for login requests.
 */
public final class LoginProxySupport {
    private static final LoginProxyConfigPolicy LOGIN_PROXY_CONFIG_POLICY = LoginProxyConfigPolicy.getInstance();

    private LoginProxySupport() {
    }

    public static ProxyHandlingResult handleProxy(Object loginHandler, Object loginPacket) {
        byte rawConnectionTypeByte = ((Number) Bridge.readField(loginPacket, "d")).byteValue();
        ConnectionType connectionType = resolveConnectionType(rawConnectionTypeByte);
        int rawConnectionType = rawConnectionTypeByte;
        boolean usingReleaseToBeta = false;

        if ((Boolean) PoseidonConfig.getInstance().getConfigOption(LOGIN_PROXY_CONFIG_POLICY.bungeeModeEnabledKey())
                && !connectionType.equals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING)
                && !connectionType.equals(ConnectionType.BUNGEECORD_ONLINE_MODE_IP_FORWARDING)) {
            String username = String.valueOf(Bridge.readField(loginPacket, "name"));
            Bridge.logInfo(loginHandler, username + " is not using BungeeCord, kicking the player.");
            Bridge.invoke(loginHandler, "disconnect", (String) PoseidonConfig.getInstance().getConfigOption(
                    LOGIN_PROXY_CONFIG_POLICY.bungeeModeKickMessageKey()
            ));
            return ProxyHandlingResult.rejected(connectionType, rawConnectionType, usingReleaseToBeta);
        }

        if (connectionType.equals(ConnectionType.RELEASE2BETA_OFFLINE_MODE_IP_FORWARDING)
                || connectionType.equals(ConnectionType.RELEASE2BETA_ONLINE_MODE_IP_FORWARDING)
                || connectionType.equals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING)
                || connectionType.equals(ConnectionType.BUNGEECORD_ONLINE_MODE_IP_FORWARDING)) {
            if ((Boolean) PoseidonConfig.getInstance().getConfigOption(
                    LOGIN_PROXY_CONFIG_POLICY.release2BetaIpForwardingEnabledKey()
            )) {
                Object socket = Bridge.invoke(loginHandler, "getSocket");
                String remoteAddress = String.valueOf(Bridge.invoke(Bridge.invoke(socket, "getInetAddress"), "getHostAddress"));
                if (remoteAddress
                        .equalsIgnoreCase(String.valueOf(PoseidonConfig.getInstance().getConfigOption(
                                LOGIN_PROXY_CONFIG_POLICY.release2BetaProxyIpKey(),
                                LOGIN_PROXY_CONFIG_POLICY.release2BetaProxyIpDefault()
                        )))) {
                    InetSocketAddress address = deserializeAddress(((Number) Bridge.readField(loginPacket, "c")).longValue());
                    String username = String.valueOf(Bridge.readField(loginPacket, "name"));
                    Bridge.logInfo(loginHandler, username + " has been detected using Release2Beta, using the IP passed through: " + address.getAddress().getHostAddress());
                    Object networkManager = Bridge.readField(loginHandler, "networkManager");
                    Bridge.invoke(networkManager, "setSocketAddress", address);
                    usingReleaseToBeta = true;
                } else {
                    String username = String.valueOf(Bridge.readField(loginPacket, "name"));
                    Bridge.logInfo(loginHandler, username + " is attempting to use a unauthorized Release2Beta server, kicking the player.");
                    Bridge.invoke(loginHandler, "disconnect", ChatColor.RED + "The Release2Beta server you are connecting through is unauthorized.");
                    return ProxyHandlingResult.rejected(connectionType, rawConnectionType, usingReleaseToBeta);
                }
            } else {
                String username = String.valueOf(Bridge.readField(loginPacket, "name"));
                Bridge.logInfo(loginHandler, username + " is trying to connect through R2B with IP Forwarding enabled, however, it is disabled in Poseidon. Kicking player!");
                Bridge.invoke(loginHandler, "disconnect", ChatColor.RED + "IP Forwarding is disabled in Poseidon. Please disable in Release2Beta.");
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

    private static final class Bridge {
        private static Object readField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static void logInfo(Object loginHandler, String message) {
            try {
                java.lang.reflect.Field loggerField = loginHandler.getClass().getField("a");
                Object logger = loggerField.get(null);
                invoke(logger, "info", message);
            } catch (Exception ignored) {
            }
        }
    }
}
