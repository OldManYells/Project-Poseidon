package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginTickService;
import net.minecraft.server.Packet1Login;
import org.junit.Assert;
import org.junit.Test;

public class LoginTickServiceTest {
    @Test
    public void tickDecisionProcessesDeferredLoginAndIncrementsCounter() {
        LoginTickService service = LoginTickService.getInstance();
        LoginTickService.TickDecision decision = service.evaluateTick(new Packet1Login(), 10, 600);

        Assert.assertTrue(decision.shouldProcessDeferredLogin());
        Assert.assertFalse(decision.shouldDisconnectForTimeout());
        Assert.assertEquals(11, decision.getNextTimeoutCounter());
        Assert.assertNull(decision.getTimeoutKickMessage());
    }

    @Test
    public void tickDecisionDisconnectsAtTimeoutThreshold() {
        LoginTickService service = LoginTickService.getInstance();
        LoginTickService.TickDecision decision = service.evaluateTick(null, 600, 600);

        Assert.assertFalse(decision.shouldProcessDeferredLogin());
        Assert.assertTrue(decision.shouldDisconnectForTimeout());
        Assert.assertEquals(601, decision.getNextTimeoutCounter());
        Assert.assertEquals("Took too long to log in", decision.getTimeoutKickMessage());
    }
}
