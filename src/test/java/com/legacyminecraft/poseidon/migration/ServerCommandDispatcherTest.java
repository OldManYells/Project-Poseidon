package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerCommandDispatcher;
import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;
import net.minecraft.server.ICommandListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ServerCommandDispatcherTest {
    @Test
    public void drainQueuedCommandsSkipsWhenBukkitServerNotReady() {
        ServerCommandDispatcher dispatcher = ServerCommandDispatcher.getInstance();
        List<ServerCommandEnvelopeBehaviour.ServerCommandState> pendingCommands =
                new ArrayList<ServerCommandEnvelopeBehaviour.ServerCommandState>();
        pendingCommands.add(
                ServerCommandEnvelopeBehaviour.getInstance().createState("stop", new TestCommandListener())
        );

        dispatcher.drainQueuedCommands(pendingCommands, null, null);

        Assert.assertEquals(0, pendingCommands.size());
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
