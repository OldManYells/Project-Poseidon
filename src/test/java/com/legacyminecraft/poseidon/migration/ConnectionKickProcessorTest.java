package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionKickProcessor;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionKickProcessorTest {
    @Test
    public void buildsLeaveMessageFromTemplate() {
        ConnectionKickProcessor processor = ConnectionKickProcessor.getInstance();
        String message = processor.buildLeaveMessage("%player% left the game.", "Notch");
        Assert.assertEquals("Notch left the game.", message);
    }
}
