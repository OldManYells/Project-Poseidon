package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.SignUpdatePacketExecutionSystem;
import net.minecraft.server.Packet130UpdateSign;
import org.junit.Assert;
import org.junit.Test;

public class SignUpdatePacketExecutionSystemTest {
    private final SignUpdatePacketExecutionSystem signUpdatePacketExecutionSystem = SignUpdatePacketExecutionSystem.getInstance();

    @Test
    public void executeSkipsWhenPlayerDead() {
        ActionCapture actionCapture = new ActionCapture();

        signUpdatePacketExecutionSystem.execute(true, new Packet130UpdateSign(), actionCapture);

        Assert.assertFalse(actionCapture.processed);
        Assert.assertNull(actionCapture.packet130UpdateSign);
    }

    @Test
    public void executeProcessesWhenPlayerAlive() {
        ActionCapture actionCapture = new ActionCapture();
        Packet130UpdateSign packet130UpdateSign = new Packet130UpdateSign();

        signUpdatePacketExecutionSystem.execute(false, packet130UpdateSign, actionCapture);

        Assert.assertTrue(actionCapture.processed);
        Assert.assertSame(packet130UpdateSign, actionCapture.packet130UpdateSign);
    }

    private static final class ActionCapture implements SignUpdatePacketExecutionSystem.SignUpdateActions {
        private boolean processed;
        private Packet130UpdateSign packet130UpdateSign;

        @Override
        public void process(Packet130UpdateSign packet130updateSign) {
            this.processed = true;
            this.packet130UpdateSign = packet130updateSign;
        }
    }
}
