package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingAttachmentSystem;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackingAttachmentServiceTest {
    @Test
    public void initialVelocityFlagMatchesMovingState() {
        EntityTrackingAttachmentSystem service = EntityTrackingAttachmentSystem.getInstance();

        Assert.assertTrue(service.shouldSendInitialVelocity(true));
        Assert.assertFalse(service.shouldSendInitialVelocity(false));
    }
}
