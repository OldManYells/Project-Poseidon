package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.LoginProxyConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class LoginProxyConfigPolicyTest {
    @Test
    public void exposesProxyConfigKeysAndDefaults() {
        LoginProxyConfigPolicy policy = LoginProxyConfigPolicy.getInstance();

        Assert.assertEquals("settings.bungeecord.bungee-mode.enable", policy.bungeeModeEnabledKey());
        Assert.assertEquals("settings.bungeecord.bungee-mode.kick-message", policy.bungeeModeKickMessageKey());
        Assert.assertEquals("settings.release2beta.enable-ip-pass-through", policy.release2BetaIpForwardingEnabledKey());
        Assert.assertEquals("settings.release2beta.proxy-ip", policy.release2BetaProxyIpKey());
        Assert.assertEquals("127.0.0.1", policy.release2BetaProxyIpDefault());
    }
}
