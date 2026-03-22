package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SlowSandInteractionBehaviour;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;
import org.junit.Assert;
import org.junit.Test;

public class SlowSandInteractionServiceTest {
    @Test
    public void collisionBoxAndSlowdownRulesMatchLegacyBehavior() {
        SlowSandInteractionBehaviour service = SlowSandInteractionBehaviour.getInstance();

        AxisAlignedBB box = service.resolveCollisionBox(10, 64, 10, 0.125F);
        Assert.assertEquals(10.0D, box.a, 0.0D);
        Assert.assertEquals(64.875D, box.e, 0.0D);
        Assert.assertEquals(11.0D, box.d, 0.0D);

        DummyEntity entity = new DummyEntity();
        entity.motX = 1.0D;
        entity.motZ = -2.0D;
        service.applyHorizontalSlowdown(entity, service.slowdownFactor());
        Assert.assertEquals(0.4D, entity.motX, 0.0D);
        Assert.assertEquals(-0.8D, entity.motZ, 0.0D);
    }

    private static final class DummyEntity extends Entity {
        private DummyEntity() {
            super((World) null);
        }

        protected void b() {
        }

        protected void a(NBTTagCompound nbttagcompound) {
        }

        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
