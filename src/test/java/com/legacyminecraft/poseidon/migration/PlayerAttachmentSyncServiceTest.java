package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerAttachmentSyncSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerAttachmentSyncServiceTest {
    @Test
    public void attachmentSyncRequiresNetHandler() {
        PlayerAttachmentSyncSystem service = PlayerAttachmentSyncSystem.getInstance();

        Assert.assertTrue(service.shouldSyncAttachment(true));
        Assert.assertFalse(service.shouldSyncAttachment(false));
    }
}
