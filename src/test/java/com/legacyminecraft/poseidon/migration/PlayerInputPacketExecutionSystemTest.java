package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerInputPacketExecutionSystem;
import net.minecraft.server.Packet27;
import org.junit.Assert;
import org.junit.Test;

public class PlayerInputPacketExecutionSystemTest {
    private final PlayerInputPacketExecutionSystem playerInputPacketExecutionSystem = PlayerInputPacketExecutionSystem.getInstance();

    @Test
    public void executeForwardsAllPacketInputValues() {
        Packet27 packet27 = new TestPacket27(1.0F, 2.0F, true, false, 3.0F, 4.0F);
        ActionCapture actionCapture = new ActionCapture();

        playerInputPacketExecutionSystem.execute(packet27, actionCapture);

        Assert.assertEquals(1.0F, actionCapture.primaryX, 0.0001F);
        Assert.assertEquals(2.0F, actionCapture.primaryY, 0.0001F);
        Assert.assertTrue(actionCapture.primaryFlag);
        Assert.assertFalse(actionCapture.secondaryFlag);
        Assert.assertEquals(3.0F, actionCapture.secondaryX, 0.0001F);
        Assert.assertEquals(4.0F, actionCapture.secondaryY, 0.0001F);
    }

    private static final class ActionCapture implements PlayerInputPacketExecutionSystem.InputActions {
        private float primaryX;
        private float primaryY;
        private boolean primaryFlag;
        private boolean secondaryFlag;
        private float secondaryX;
        private float secondaryY;

        @Override
        public void applyInput(float primaryX, float primaryY, boolean primaryFlag, boolean secondaryFlag, float secondaryX, float secondaryY) {
            this.primaryX = primaryX;
            this.primaryY = primaryY;
            this.primaryFlag = primaryFlag;
            this.secondaryFlag = secondaryFlag;
            this.secondaryX = secondaryX;
            this.secondaryY = secondaryY;
        }
    }

    private static final class TestPacket27 extends Packet27 {
        private final float primaryX;
        private final float primaryY;
        private final boolean primaryFlag;
        private final boolean secondaryFlag;
        private final float secondaryX;
        private final float secondaryY;

        private TestPacket27(float primaryX, float primaryY, boolean primaryFlag, boolean secondaryFlag, float secondaryX, float secondaryY) {
            this.primaryX = primaryX;
            this.primaryY = primaryY;
            this.primaryFlag = primaryFlag;
            this.secondaryFlag = secondaryFlag;
            this.secondaryX = secondaryX;
            this.secondaryY = secondaryY;
        }

        @Override
        public float c() {
            return primaryX;
        }

        @Override
        public float e() {
            return primaryY;
        }

        @Override
        public boolean g() {
            return primaryFlag;
        }

        @Override
        public boolean h() {
            return secondaryFlag;
        }

        @Override
        public float d() {
            return secondaryX;
        }

        @Override
        public float f() {
            return secondaryY;
        }
    }
}
