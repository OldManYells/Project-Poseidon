package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginConnectionLifecycleService;
import org.junit.Assert;
import org.junit.Test;

public class LoginConnectionLifecycleServiceTest {
    @Test
    public void lifecycleMessagesMatchLegacyText() {
        LoginConnectionLifecycleService service = LoginConnectionLifecycleService.getInstance();

        Assert.assertEquals(
                "Disconnecting Steve [/127.0.0.1:25565]: Took too long to log in",
                service.createDisconnectLogMessage("Steve [/127.0.0.1:25565]", "Took too long to log in")
        );
        Assert.assertEquals(
                "Steve [/127.0.0.1:25565] lost connection",
                service.createConnectionLostLogMessage("Steve [/127.0.0.1:25565]")
        );
        Assert.assertEquals("Protocol error", service.getProtocolErrorMessage());
    }
}
