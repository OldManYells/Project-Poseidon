package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingFrameBehaviour;
import com.legacyminecraft.poseidon.entity.EntityTrackingTickPolicy;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackingFrameBehaviourTest {
    private final EntityTrackingFrameBehaviour behaviour = EntityTrackingFrameBehaviour.getInstance();

    @Test
    public void decideFrameIncrementsCounterAndUsesTickPolicy() {
        Entity tracker = new DummyEntity();

        EntityTrackingFrameBehaviour.FrameDecision decision = behaviour.decideFrame(
                tracker,
                EntityTrackingTickPolicy.getInstance(),
                false,
                0.0D,
                0.0D,
                0.0D,
                0,
                2
        );

        Assert.assertEquals(1, decision.getNextTickCounter());
        Assert.assertTrue(decision.shouldRescanPlayers());
        Assert.assertFalse(decision.shouldProcessTrackingFrame());
    }

    private static final class DummyEntity extends Entity {
        private DummyEntity() {
            super(null);
        }

        protected void b() {
        }

        protected void a(NBTTagCompound nbttagcompound) {
        }

        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
