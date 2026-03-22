package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.LoginHandshakeSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class LoginHandshakeServiceTest {
    private final LoginHandshakeSystem service = LoginHandshakeSystem.getInstance();

    @Test
    public void createsDeterministicServerIdFromRandom() {
        String serverId = service.createServerId(new Random(12345L));
        Assert.assertEquals("5c9f20d58361b331", serverId);
    }

    @Test
    public void resolvesHandshakeTokenByMode() {
        Assert.assertEquals("abc", service.resolveHandshakeToken(true, "abc"));
        Assert.assertEquals("-", service.resolveHandshakeToken(false, "abc"));
    }
}
