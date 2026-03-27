package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerLeaveMessageConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class PlayerLeaveMessageConfigPolicyTest {
    @Test
    public void exposesLeaveMessageConfigKey() {
        PlayerLeaveMessageConfigPolicy policy = PlayerLeaveMessageConfigPolicy.getInstance();

        Assert.assertEquals("message.player.leave", policy.playerLeaveMessageKey());
    }
}
