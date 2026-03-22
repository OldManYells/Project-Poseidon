package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.InvalidPositionResponseSystem;
import org.junit.Assert;
import org.junit.Test;

public class InvalidPositionResponseServiceTest {
    @Test
    public void invalidPositionMessagesMatchLegacyBehavior() {
        InvalidPositionResponseSystem service = InvalidPositionResponseSystem.getInstance();

        Assert.assertEquals(
                "Alex was caught trying to crash the server with an invalid position.",
                service.createInvalidPositionLogMessage("Alex")
        );
        Assert.assertEquals("Nope!", service.getInvalidPositionKickMessage());
    }
}
