package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ServerAccessMessageConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ServerAccessMessageConfigPolicyTest {
    @Test
    public void exposesAdmissionAndJoinMessageKeys() {
        ServerAccessMessageConfigPolicy policy = ServerAccessMessageConfigPolicy.getInstance();

        Assert.assertEquals("message.kick.banned", policy.kickBannedKey());
        Assert.assertEquals("message.kick.ip-banned", policy.kickIpBannedKey());
        Assert.assertEquals("message.kick.not-whitelisted", policy.kickNotWhitelistedKey());
        Assert.assertEquals("message.kick.full", policy.kickFullKey());
        Assert.assertEquals("message.player.join", policy.playerJoinKey());
    }
}
