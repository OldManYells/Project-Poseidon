package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.LeverPlacementAndPowerBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class LeverPlacementAndPowerServiceTest {
    @Test
    public void placementRulesMatchLegacySupportChecks() {
        LeverPlacementAndPowerBehaviour service = LeverPlacementAndPowerBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(10, 63, 10);
        support.add(9, 64, 10);

        Assert.assertTrue(service.canPlaceOnSide(support, 10, 64, 10, 1));
        Assert.assertTrue(service.canPlaceOnSide(support, 10, 64, 10, 5));
        Assert.assertFalse(service.canPlaceOnSide(support, 10, 64, 10, 2));
        Assert.assertTrue(service.canPlace(support, 10, 64, 10));
    }

    @Test
    public void resolvePostPlaceDataMatchesLegacyOrientationAndPowerBitBehavior() {
        LeverPlacementAndPowerBehaviour service = LeverPlacementAndPowerBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(10, 63, 10);
        support.add(9, 64, 10);

        Assert.assertEquals(5, service.resolvePostPlaceData(support, new FixedRandom(0), 10, 64, 10, 1, 0));
        Assert.assertEquals(6, service.resolvePostPlaceData(support, new FixedRandom(1), 10, 64, 10, 1, 0));
        Assert.assertEquals(9, service.resolvePostPlaceData(support, new FixedRandom(0), 10, 64, 10, 5, 8));
        Assert.assertEquals(-1, service.resolvePostPlaceData(new StubSupportQuery(), new FixedRandom(0), 10, 64, 10, 1, 0));
    }

    @Test
    public void attachmentAndBoundsRulesMatchLegacyDataMappings() {
        LeverPlacementAndPowerBehaviour service = LeverPlacementAndPowerBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();

        Assert.assertTrue(service.isAttachedSupportMissing(support, 10, 64, 10, 1));
        support.add(9, 64, 10);
        Assert.assertFalse(service.isAttachedSupportMissing(support, 10, 64, 10, 1));

        LeverPlacementAndPowerBehaviour.Bounds sideBounds = service.resolveBounds(1);
        Assert.assertEquals(0.0F, sideBounds.getMinX(), 0.0F);
        Assert.assertEquals(0.8F, sideBounds.getMaxY(), 0.0F);

        LeverPlacementAndPowerBehaviour.Bounds topBounds = service.resolveBounds(6);
        Assert.assertEquals(0.0F, topBounds.getMinY(), 0.0F);
        Assert.assertEquals(0.6F, topBounds.getMaxY(), 0.0F);
    }

    @Test
    public void powerSideAndNeighborOffsetRulesMatchLegacyLeverBehavior() {
        LeverPlacementAndPowerBehaviour service = LeverPlacementAndPowerBehaviour.getInstance();

        int poweredFloorLever = service.composeData(6, 8);
        Assert.assertTrue(service.isPoweringSide(poweredFloorLever, 1));
        Assert.assertFalse(service.isPoweringSide(poweredFloorLever, 2));

        LeverPlacementAndPowerBehaviour.NeighborOffset west = service.resolveAttachmentOffset(1);
        Assert.assertEquals(-1, west.getX());
        Assert.assertEquals(0, west.getY());
        Assert.assertEquals(0, west.getZ());

        LeverPlacementAndPowerBehaviour.NeighborOffset floor = service.resolveAttachmentOffset(6);
        Assert.assertEquals(0, floor.getX());
        Assert.assertEquals(-1, floor.getY());
        Assert.assertEquals(0, floor.getZ());
    }

    private static final class StubSupportQuery implements LeverPlacementAndPowerBehaviour.SupportQuery {
        private final Set solidKeys = new HashSet();

        public void add(int x, int y, int z) {
            solidKeys.add(key(x, y, z));
        }

        public boolean isBlockSolid(int x, int y, int z) {
            return solidKeys.contains(key(x, y, z));
        }

        private String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }
    }

    private static final class FixedRandom implements LeverPlacementAndPowerBehaviour.RandomSource {
        private final int[] values;
        private int index;

        private FixedRandom(int... values) {
            this.values = values;
        }

        public int nextInt(int bound) {
            if (index >= values.length) {
                throw new AssertionError("Unexpected random call");
            }

            int value = values[index++];
            if (value < 0 || value >= bound) {
                throw new AssertionError("Invalid random value " + value + " for bound " + bound);
            }
            return value;
        }
    }
}
