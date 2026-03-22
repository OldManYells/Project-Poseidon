package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.ButtonPlacementAndPowerBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ButtonPlacementAndPowerServiceTest {
    @Test
    public void canPlaceChecksExpectedNeighborSupport() {
        ButtonPlacementAndPowerBehaviour service = ButtonPlacementAndPowerBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(10, 64, 11);
        support.add(9, 64, 10);

        Assert.assertTrue(service.canPlaceOnSide(support, 10, 64, 10, 2));
        Assert.assertFalse(service.canPlaceOnSide(support, 10, 64, 10, 3));
        Assert.assertTrue(service.canPlace(support, 10, 64, 10));
    }

    @Test
    public void resolvePostPlaceDataPreservesPowerBitAndResolvesFacing() {
        ButtonPlacementAndPowerBehaviour service = ButtonPlacementAndPowerBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(9, 64, 10);

        Assert.assertEquals(9, service.resolvePostPlaceData(support, 10, 64, 10, 5, 8));

        StubSupportQuery fallbackSupport = new StubSupportQuery();
        fallbackSupport.add(11, 64, 10);
        Assert.assertEquals(2, service.resolvePostPlaceData(fallbackSupport, 10, 64, 10, 1, 0));
    }

    @Test
    public void attachmentValidationAndBoundingRulesMatchLegacyDataMapping() {
        ButtonPlacementAndPowerBehaviour service = ButtonPlacementAndPowerBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();

        Assert.assertTrue(service.isAttachedSupportMissing(support, 10, 64, 10, 1));

        support.add(9, 64, 10);
        Assert.assertFalse(service.isAttachedSupportMissing(support, 10, 64, 10, 1));

        ButtonPlacementAndPowerBehaviour.Bounds unpressed = service.resolveBounds(1);
        Assert.assertNotNull(unpressed);
        Assert.assertEquals(0.125F, unpressed.getMaxX(), 0.0F);

        ButtonPlacementAndPowerBehaviour.Bounds pressed = service.resolveBounds(9);
        Assert.assertNotNull(pressed);
        Assert.assertEquals(0.0625F, pressed.getMaxX(), 0.0F);
    }

    @Test
    public void powerAndAttachmentDirectionRulesMatchLegacyFacingPolicy() {
        ButtonPlacementAndPowerBehaviour service = ButtonPlacementAndPowerBehaviour.getInstance();

        int poweredNorthFaceData = service.composeData(4, 8);
        Assert.assertTrue(service.isPoweringSide(poweredNorthFaceData, 2));
        Assert.assertFalse(service.isPoweringSide(poweredNorthFaceData, 1));

        ButtonPlacementAndPowerBehaviour.NeighborOffset zNegative = service.resolveAttachmentOffset(3);
        Assert.assertEquals(0, zNegative.getX());
        Assert.assertEquals(0, zNegative.getY());
        Assert.assertEquals(-1, zNegative.getZ());

        ButtonPlacementAndPowerBehaviour.NeighborOffset fallback = service.resolveAttachmentOffset(0);
        Assert.assertEquals(0, fallback.getX());
        Assert.assertEquals(-1, fallback.getY());
        Assert.assertEquals(0, fallback.getZ());
    }

    private static final class StubSupportQuery implements ButtonPlacementAndPowerBehaviour.SupportQuery {
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
}
