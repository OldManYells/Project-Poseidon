package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SpongePhysicsBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SpongePhysicsServiceTest {
    @Test
    public void legacyRemovalScansFullCubeAndAppliesPhysicsForEveryPosition() {
        SpongePhysicsBehaviour service = SpongePhysicsBehaviour.getInstance();
        Assert.assertEquals(2, service.removalRadius());
        Assert.assertTrue(service.shouldUseOptimizedRemoval(true));
        Assert.assertFalse(service.shouldUseOptimizedRemoval(false));

        AtomicInteger calls = new AtomicInteger();
        service.applyLegacyRemoval(new SpongePhysicsBehaviour.PhysicsWorld() {
            public int getTypeId(int x, int y, int z) {
                return x + y + z;
            }

            public void applyPhysics(int x, int y, int z, int typeId) {
                calls.incrementAndGet();
            }
        }, 10, 64, 10, 2);
        Assert.assertEquals(125, calls.get());
    }

    @Test
    public void optimizedRemovalAppliesPhysicsOnlyToWaterInsideHeightBounds() {
        SpongePhysicsBehaviour service = SpongePhysicsBehaviour.getInstance();
        Map<String, Integer> blocks = new HashMap<String, Integer>();
        blocks.put("10:64:10", 8);
        blocks.put("9:64:10", 9);
        blocks.put("8:-1:10", 8);
        blocks.put("12:128:10", 8);
        blocks.put("10:64:9", 1);

        AtomicInteger calls = new AtomicInteger();
        service.applyOptimizedRemoval(new SpongePhysicsBehaviour.PhysicsWorld() {
            public int getTypeId(int x, int y, int z) {
                Integer id = blocks.get(x + ":" + y + ":" + z);
                return id == null ? 0 : id;
            }

            public void applyPhysics(int x, int y, int z, int typeId) {
                calls.incrementAndGet();
            }
        }, 10, 64, 10, 2, 0, 127, 8, 9);
        Assert.assertEquals(2, calls.get());
    }
}
