package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ConsoleHelpMessageService;
import net.minecraft.server.ICommandListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConsoleHelpMessageServiceTest {
    @Test
    public void emitsLegacyHelpLinesInOrder() {
        CapturingCommandListener listener = new CapturingCommandListener();

        ConsoleHelpMessageService.getInstance().sendHelp(listener);

        Assert.assertEquals("To run the server without a gui, start it like this:", listener.messages.get(0));
        Assert.assertEquals("   time <add|set> <amount>   adds to or sets the world time (0-24000)", listener.messages.get(listener.messages.size() - 1));
        Assert.assertTrue(listener.messages.size() >= 10);
    }

    private static final class CapturingCommandListener implements ICommandListener {
        private final List<String> messages = new ArrayList<String>();

        @Override
        public void sendMessage(String s) {
            messages.add(s);
        }

        @Override
        public String getName() {
            return "tester";
        }
    }
}
