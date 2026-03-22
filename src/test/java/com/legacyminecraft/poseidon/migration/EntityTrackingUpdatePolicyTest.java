package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingUpdatePolicy;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackingUpdatePolicyTest {
    @Test
    public void relativePacketBoundsMatchLegacyThresholds() {
        EntityTrackingUpdatePolicy policy = EntityTrackingUpdatePolicy.getInstance();

        Assert.assertTrue(policy.canUseRelativePacket(0, 0, 0, 0));
        Assert.assertTrue(policy.canUseRelativePacket(127, -128, 0, 400));
        Assert.assertFalse(policy.canUseRelativePacket(128, 0, 0, 0));
        Assert.assertFalse(policy.canUseRelativePacket(0, 0, 0, 401));
    }

    @Test
    public void movementAndRotationThresholdChecksAreOneUnit() {
        EntityTrackingUpdatePolicy policy = EntityTrackingUpdatePolicy.getInstance();

        Assert.assertFalse(policy.needsPositionUpdate(null, 0, 0, 0));
        Assert.assertTrue(policy.needsPositionUpdate(null, 1, 0, 0));

        Assert.assertFalse(policy.needsRotationUpdate(10, 20, 10, 20));
        Assert.assertTrue(policy.needsRotationUpdate(11, 20, 10, 20));
    }

    @Test
    public void velocityUpdateThresholdUsesLegacyDeltaRule() {
        EntityTrackingUpdatePolicy policy = EntityTrackingUpdatePolicy.getInstance();
        TestEntity tracker = new TestEntity();
        tracker.motX = 0.05D;
        tracker.motY = 0.0D;
        tracker.motZ = 0.0D;
        Assert.assertTrue(policy.shouldSendVelocityUpdate(tracker, 0.0D, 0.0D, 0.0D));

        tracker.motX = 0.001D;
        Assert.assertFalse(policy.shouldSendVelocityUpdate(tracker, 0.0D, 0.0D, 0.0D));
    }

    private static final class TestEntity extends Entity {
        private TestEntity() {
            super(null);
        }

        @Override
        protected void b() {
        }

        @Override
        protected void a(NBTTagCompound nbttagcompound) {
        }

        @Override
        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
