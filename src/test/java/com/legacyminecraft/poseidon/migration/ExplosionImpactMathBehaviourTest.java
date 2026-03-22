package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.ExplosionImpactMathBehaviour;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class ExplosionImpactMathBehaviourTest {
    private final ExplosionImpactMathBehaviour behaviour = ExplosionImpactMathBehaviour.getInstance();

    @Test
    public void computeImpactReturnsNullWhenEntityOutsideExplosionRange() {
        DummyEntity entity = new DummyEntity();
        entity.locX = 10.0D;
        entity.locY = 0.0D;
        entity.locZ = 0.0D;

        ExplosionImpactMathBehaviour.ImpactComputation impact = behaviour.computeImpact(entity, 0.0D, 0.0D, 0.0D, 4.0F, 1.0D);
        Assert.assertNull(impact);
    }

    @Test
    public void applyKnockbackAddsVelocityAndOptionalVelocityChangedFlag() {
        DummyEntity entity = new DummyEntity();
        ExplosionImpactMathBehaviour.ImpactComputation impact = behaviour.computeImpact(entity, -1.0D, 0.0D, 0.0D, 4.0F, 1.0D);

        Assert.assertNotNull(impact);
        behaviour.applyKnockback(entity, impact, true);
        Assert.assertTrue(entity.motX > 0.0D);
        Assert.assertTrue(entity.velocityChanged);
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
