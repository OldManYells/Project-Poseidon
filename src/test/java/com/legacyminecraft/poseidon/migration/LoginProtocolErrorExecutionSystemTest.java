package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginProtocolErrorExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginProtocolErrorExecutionSystemTest {
    private final LoginProtocolErrorExecutionSystem loginProtocolErrorExecutionSystem = LoginProtocolErrorExecutionSystem.getInstance();

    @Test
    public void executeDisconnectsWithProvidedProtocolMessage() {
        ActionCapture actionCapture = new ActionCapture();

        loginProtocolErrorExecutionSystem.execute("Protocol error", actionCapture);

        Assert.assertEquals("Protocol error", actionCapture.message);
    }

    private static final class ActionCapture implements LoginProtocolErrorExecutionSystem.ProtocolErrorActions {
        private String message;

        @Override
        public void disconnect(String message) {
            this.message = message;
        }
    }
}
