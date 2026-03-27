package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkSocketInitializationFailureBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NetworkSocketInitializationFailureBehaviourTest {
    private final NetworkSocketInitializationFailureBehaviour behaviour =
            NetworkSocketInitializationFailureBehaviour.getInstance();

    @Test
    public void resolveMessageUsesExceptionMessageWhenPresent() {
        Assert.assertEquals("boom", behaviour.resolveMessage(new IOException("boom")));
    }

    @Test
    public void resolveMessageFallsBackForNullOrBlankMessages() {
        Assert.assertEquals("Socket stream initialization failed", behaviour.resolveMessage(new IOException()));
        Assert.assertEquals("Socket stream initialization failed", behaviour.resolveMessage(new IOException("   ")));
        Assert.assertEquals("Socket stream initialization failed", behaviour.resolveMessage(null));
    }
}
