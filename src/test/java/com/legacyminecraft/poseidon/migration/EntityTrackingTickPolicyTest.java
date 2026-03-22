package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingTickPolicy;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackingTickPolicyTest {
    @Test
    public void rescanDependsOnAnchorDistanceOrMissingAnchor() {
        EntityTrackingTickPolicy policy = EntityTrackingTickPolicy.getInstance();
        NonPlayerEntity tracker = new NonPlayerEntity();

        tracker.locX = 5.0D;
        tracker.locY = 0.0D;
        tracker.locZ = 0.0D;

        Assert.assertTrue(policy.shouldRescanTrackedPlayers(false, tracker, 5.0D, 0.0D, 0.0D));
        Assert.assertFalse(policy.shouldRescanTrackedPlayers(true, tracker, 5.0D, 0.0D, 0.0D));
        Assert.assertTrue(policy.shouldRescanTrackedPlayers(true, tracker, 0.0D, 0.0D, 0.0D));
    }

    @Test
    public void frameProcessingDependsOnIntervalOrDynamicState() {
        EntityTrackingTickPolicy policy = EntityTrackingTickPolicy.getInstance();
        NonPlayerEntity tracker = new NonPlayerEntity();

        Assert.assertFalse(policy.shouldProcessTrackingFrame(1, 20, tracker));
        Assert.assertTrue(policy.shouldProcessTrackingFrame(20, 20, tracker));

        tracker.airBorne = true;
        Assert.assertTrue(policy.shouldProcessTrackingFrame(1, 20, tracker));
    }

    private static final class NonPlayerEntity extends Entity {
        private NonPlayerEntity() {
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
