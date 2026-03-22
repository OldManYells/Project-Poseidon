package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginIdentityService;
import org.junit.Assert;
import org.junit.Test;

public class LoginIdentityServiceTest {
    @Test
    public void crackedUsernamesUseDotPrefix() {
        LoginIdentityService service = LoginIdentityService.getInstance();

        Assert.assertTrue(service.isCrackedUsername(".Steve"));
        Assert.assertFalse(service.isCrackedUsername("Steve"));
        Assert.assertFalse(service.isCrackedUsername(null));
    }

    @Test
    public void identityDescriptionMatchesLegacyShape() {
        LoginIdentityService service = LoginIdentityService.getInstance();

        Assert.assertEquals("Steve [/127.0.0.1:25565]", service.describeConnection("Steve", "/127.0.0.1:25565"));
        Assert.assertEquals("/127.0.0.1:25565", service.describeConnection(null, "/127.0.0.1:25565"));
    }
}
