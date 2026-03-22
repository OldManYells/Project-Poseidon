package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.FloatingTickGuardSystem;
import org.junit.Assert;
import org.junit.Test;

public class FloatingTickGuardServiceTest {
    @Test
    public void resetWhenFlightAllowedOrGrounded() {
        FloatingTickGuardSystem service = FloatingTickGuardSystem.getInstance();

        FloatingTickGuardSystem.FloatingDecision allowFlightDecision =
                service.evaluate(true, false, 0.0D, 7);
        Assert.assertEquals(0, allowFlightDecision.getUpdatedFloatingTicks());
        Assert.assertFalse(allowFlightDecision.shouldKick());

        FloatingTickGuardSystem.FloatingDecision groundedDecision =
                service.evaluate(false, true, 0.0D, 7);
        Assert.assertEquals(0, groundedDecision.getUpdatedFloatingTicks());
        Assert.assertFalse(groundedDecision.shouldKick());
    }

    @Test
    public void incrementAndKickAtThreshold() {
        FloatingTickGuardSystem service = FloatingTickGuardSystem.getInstance();

        FloatingTickGuardSystem.FloatingDecision incrementDecision =
                service.evaluate(false, false, 0.0D, 10);
        Assert.assertEquals(11, incrementDecision.getUpdatedFloatingTicks());
        Assert.assertFalse(incrementDecision.shouldKick());

        FloatingTickGuardSystem.FloatingDecision kickDecision =
                service.evaluate(false, false, 0.0D, 80);
        Assert.assertEquals(81, kickDecision.getUpdatedFloatingTicks());
        Assert.assertTrue(kickDecision.shouldKick());
        Assert.assertEquals("Flying is not enabled on this server", kickDecision.getDisconnectReason());
    }
}
