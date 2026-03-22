package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginAdmissionEventService;
import com.legacyminecraft.poseidon.auth.login.LoginAdmissionPolicy;
import org.bukkit.event.player.PlayerLoginEvent;
import org.junit.Assert;
import org.junit.Test;

public class LoginAdmissionEventServiceTest {
    @Test
    public void legacyLoginResultMappingMatchesDecision() {
        LoginAdmissionEventService service = LoginAdmissionEventService.getInstance();

        Assert.assertEquals(PlayerLoginEvent.Result.ALLOWED, service.toLegacyLoginResult(LoginAdmissionPolicy.Decision.ALLOWED));
        Assert.assertEquals(PlayerLoginEvent.Result.KICK_BANNED, service.toLegacyLoginResult(LoginAdmissionPolicy.Decision.KICK_BANNED));
        Assert.assertEquals(PlayerLoginEvent.Result.KICK_BANNED_IP, service.toLegacyLoginResult(LoginAdmissionPolicy.Decision.KICK_BANNED_IP));
        Assert.assertEquals(PlayerLoginEvent.Result.KICK_WHITELIST, service.toLegacyLoginResult(LoginAdmissionPolicy.Decision.KICK_WHITELIST));
        Assert.assertEquals(PlayerLoginEvent.Result.KICK_FULL, service.toLegacyLoginResult(LoginAdmissionPolicy.Decision.KICK_FULL));
    }

    @Test
    public void admissionEventResultFactoriesExposeState() {
        LoginAdmissionEventService.AdmissionEventResult allowed = LoginAdmissionEventService.AdmissionEventResult.allowed();
        Assert.assertTrue(allowed.isAllowed());
        Assert.assertNull(allowed.getKickMessage());

        LoginAdmissionEventService.AdmissionEventResult denied = LoginAdmissionEventService.AdmissionEventResult.denied("No entry");
        Assert.assertFalse(denied.isAllowed());
        Assert.assertEquals("No entry", denied.getKickMessage());
    }
}
