package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ChatMessageRouter;
import org.junit.Assert;
import org.junit.Test;

public class ChatMessageRouterTest {
    @Test
    public void commandMessageDetectionUsesSlashPrefix() {
        ChatMessageRouter router = ChatMessageRouter.getInstance();

        Assert.assertTrue(router.isCommandMessage("/help"));
        Assert.assertFalse(router.isCommandMessage("hello world"));
    }
}
