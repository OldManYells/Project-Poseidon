package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketSendPipelineSystem;
import org.junit.Assert;
import org.junit.Test;

public class PacketSendPipelineServiceTest {
    @Test
    public void nullPacketsAreDroppedBeforeDispatch() {
        PacketSendPipelineSystem service = PacketSendPipelineSystem.getInstance();

        Assert.assertFalse(service.sendPacket(null, null, null, null, false));
    }
}
