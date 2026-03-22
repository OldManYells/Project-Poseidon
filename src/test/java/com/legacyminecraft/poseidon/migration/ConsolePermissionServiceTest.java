package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ConsolePermissionService;
import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConsolePermissionServiceTest {
    @Test
    public void deniesUnknownListenerAndSendsLegacyMessage() {
        CapturingCommandListener listener = new CapturingCommandListener();
        ConsolePermissionService service = ConsolePermissionService.getInstance();

        boolean allowed = service.checkPermission((MinecraftServer) null, listener, "stop");

        Assert.assertFalse(allowed);
        Assert.assertEquals("I'm sorry, Dave, but I cannot let you do that.", listener.messages.get(0));
    }

    private static final class CapturingCommandListener implements ICommandListener {
        private final List<String> messages = new ArrayList<String>();

        @Override
        public void sendMessage(String s) {
            messages.add(s);
        }

        @Override
        public String getName() {
            return "captured";
        }
    }
}
