package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.PlayerCommandProcessor;
import org.junit.Assert;
import org.junit.Test;

public class PlayerCommandProcessorTest {
    @Test
    public void doesNotRedactWhenPoseidonServerIsUnavailable() {
        PlayerCommandProcessor processor = PlayerCommandProcessor.getInstance();
        Assert.assertFalse(processor.shouldRedactCommand("help"));
    }
}
