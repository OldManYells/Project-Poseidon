package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingMembershipSystem;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackingMembershipServiceTest {
    @Test
    public void membershipResultFactoriesExposeExpectedState() {
        EntityTrackingMembershipSystem.MembershipResult none =
                EntityTrackingMembershipSystem.MembershipResult.none();
        Assert.assertFalse(none.isStartedTracking());
        Assert.assertFalse(none.isStoppedTracking());
        Assert.assertNull(none.getMotionSnapshot());

        EntityTrackingMembershipSystem.MembershipResult stopped =
                EntityTrackingMembershipSystem.MembershipResult.stopped();
        Assert.assertFalse(stopped.isStartedTracking());
        Assert.assertTrue(stopped.isStoppedTracking());
        Assert.assertNull(stopped.getMotionSnapshot());

        EntityTrackingMembershipSystem.MembershipResult started =
                EntityTrackingMembershipSystem.MembershipResult.started(null);
        Assert.assertTrue(started.isStartedTracking());
        Assert.assertFalse(started.isStoppedTracking());
        Assert.assertNull(started.getMotionSnapshot());
    }

    @Test
    public void selfTrackingPredicateUsesReferenceEquality() {
        EntityTrackingMembershipSystem service = EntityTrackingMembershipSystem.getInstance();
        Entity tracker = new NonPlayerEntity();

        Assert.assertTrue(service.isSelfTracking(null, null));
        Assert.assertFalse(service.isSelfTracking(tracker, null));
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
