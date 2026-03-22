package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.PlayerListTickRegistrationSystem;
import net.minecraft.server.IUpdatePlayerListBox;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerListTickRegistrationSystemTest {
    private final PlayerListTickRegistrationSystem playerListTickRegistrationSystem = PlayerListTickRegistrationSystem.getInstance();

    @Test
    public void registerAddsListenerToTickList() {
        List listeners = new ArrayList();
        IUpdatePlayerListBox listener = new TestListBox();

        playerListTickRegistrationSystem.register(listeners, listener);

        Assert.assertEquals(1, listeners.size());
        Assert.assertSame(listener, listeners.get(0));
    }

    private static final class TestListBox implements IUpdatePlayerListBox {
        @Override
        public void a() {
        }
    }
}
