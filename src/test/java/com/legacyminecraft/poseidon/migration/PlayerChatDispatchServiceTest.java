package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerChatDispatchSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerChatDispatchServiceTest {
    @Test
    public void deadPlayersDoNotDispatchChat() {
        PlayerChatDispatchSystem service = PlayerChatDispatchSystem.getInstance();
        final boolean[] invoked = new boolean[]{false};

        boolean handled = service.dispatchValidatedChat(
                null,
                null,
                null,
                true,
                "/help",
                new PlayerChatDispatchSystem.CommandDispatcher() {
                    @Override
                    public void dispatch(String message) {
                        invoked[0] = true;
                    }
                }
        );

        Assert.assertFalse(handled);
        Assert.assertFalse(invoked[0]);
    }

    @Test
    public void commandMessagesInvokeDispatcher() {
        PlayerChatDispatchSystem service = PlayerChatDispatchSystem.getInstance();
        final String[] captured = new String[]{null};

        boolean handled = service.dispatchValidatedChat(
                null,
                null,
                null,
                false,
                "/kick Steve",
                new PlayerChatDispatchSystem.CommandDispatcher() {
                    @Override
                    public void dispatch(String message) {
                        captured[0] = message;
                    }
                }
        );

        Assert.assertTrue(handled);
        Assert.assertEquals("/kick Steve", captured[0]);
    }
}
