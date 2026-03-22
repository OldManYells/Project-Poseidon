package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityVelocityChangeProcessor;
import org.junit.Assert;
import org.junit.Test;

public class EntityVelocityChangeProcessorTest {
    @Test
    public void broadcastsOnlyWhenVelocityEventIsNotCancelled() {
        EntityVelocityChangeProcessor processor = EntityVelocityChangeProcessor.getInstance();

        Assert.assertTrue(processor.shouldBroadcastVelocityPacket(false));
        Assert.assertFalse(processor.shouldBroadcastVelocityPacket(true));
    }
}
