package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for proxy/IP-forwarding login admission controls.
 */
public final class LoginProxyConfigPolicy {
    private static final LoginProxyConfigPolicy INSTANCE = new LoginProxyConfigPolicy();
    private static final String BUNGEE_MODE_ENABLED_KEY = "settings.bungeecord.bungee-mode.enable";
    private static final String BUNGEE_MODE_KICK_MESSAGE_KEY = "settings.bungeecord.bungee-mode.kick-message";
    private static final String RELEASE2BETA_IP_FORWARDING_ENABLED_KEY = "settings.release2beta.enable-ip-pass-through";
    private static final String RELEASE2BETA_PROXY_IP_KEY = "settings.release2beta.proxy-ip";
    private static final String RELEASE2BETA_PROXY_IP_DEFAULT = "127.0.0.1";

    private LoginProxyConfigPolicy() {
    }

    public static LoginProxyConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String bungeeModeEnabledKey() {
        return BUNGEE_MODE_ENABLED_KEY;
    }

    public String bungeeModeKickMessageKey() {
        return BUNGEE_MODE_KICK_MESSAGE_KEY;
    }

    public String release2BetaIpForwardingEnabledKey() {
        return RELEASE2BETA_IP_FORWARDING_ENABLED_KEY;
    }

    public String release2BetaProxyIpKey() {
        return RELEASE2BETA_PROXY_IP_KEY;
    }

    public String release2BetaProxyIpDefault() {
        return RELEASE2BETA_PROXY_IP_DEFAULT;
    }
}
