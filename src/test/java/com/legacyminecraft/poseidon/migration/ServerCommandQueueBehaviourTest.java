package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;
import com.legacyminecraft.poseidon.runtime.ServerCommandQueueBehaviour;
import net.minecraft.server.ICommandListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ServerCommandQueueBehaviourTest {
    private final ServerCommandQueueBehaviour serverCommandQueueBehaviour = ServerCommandQueueBehaviour.getInstance();

    @Test
    public void enqueueAddsServerCommandEnvelopeToQueue() {
        List<ServerCommandEnvelopeBehaviour.ServerCommandState> commandQueue =
                new ArrayList<ServerCommandEnvelopeBehaviour.ServerCommandState>();
        ICommandListener commandListener = new TestCommandListener();

        serverCommandQueueBehaviour.enqueue(commandQueue, "say hello", commandListener);

        Assert.assertEquals(1, commandQueue.size());
        Assert.assertTrue(commandQueue.get(0) instanceof ServerCommandEnvelopeBehaviour.ServerCommandState);
        ServerCommandEnvelopeBehaviour.ServerCommandState serverCommandState = commandQueue.get(0);
        Assert.assertEquals("say hello", serverCommandState.getCommandText());
        Assert.assertSame(commandListener, serverCommandState.getCommandListener());
    }

    private static final class TestCommandListener implements ICommandListener {
        @Override
        public void sendMessage(String s) {
        }

        @Override
        public String getName() {
            return "test-listener";
        }
    }
}
