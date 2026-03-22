package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.LadderPlacementAndBoundsBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class LadderPlacementAndBoundsServiceTest {
    @Test
    public void canPlaceAndAttachmentRulesMatchLegacySupportChecks() {
        LadderPlacementAndBoundsBehaviour service = LadderPlacementAndBoundsBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(9, 64, 10);
        support.add(10, 64, 11);

        Assert.assertTrue(service.canPlace(support, 10, 64, 10));
        Assert.assertTrue(service.hasValidAttachment(support, 10, 64, 10, 2));
        Assert.assertFalse(service.hasValidAttachment(support, 10, 64, 10, 4));
    }

    @Test
    public void resolvePostPlaceDataMatchesLegacyFaceSelection() {
        LadderPlacementAndBoundsBehaviour service = LadderPlacementAndBoundsBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(10, 64, 11);
        support.add(11, 64, 10);
        support.add(9, 64, 10);

        Assert.assertEquals(2, service.resolvePostPlaceData(support, 0, 2, 10, 64, 10));
        Assert.assertEquals(4, service.resolvePostPlaceData(support, 0, 4, 10, 64, 10));
        Assert.assertEquals(5, service.resolvePostPlaceData(support, 5, 3, 10, 64, 10));
    }

    @Test
    public void resolveBoundsMatchesLegacyThicknessLayouts() {
        LadderPlacementAndBoundsBehaviour service = LadderPlacementAndBoundsBehaviour.getInstance();

        LadderPlacementAndBoundsBehaviour.Bounds north = service.resolveBounds(2, 0.125F);
        Assert.assertNotNull(north);
        Assert.assertEquals(0.875F, north.getMinZ(), 0.0F);

        LadderPlacementAndBoundsBehaviour.Bounds west = service.resolveBounds(5, 0.125F);
        Assert.assertNotNull(west);
        Assert.assertEquals(0.125F, west.getMaxX(), 0.0F);

        Assert.assertNull(service.resolveBounds(0, 0.125F));
    }

    private static final class StubSupportQuery implements LadderPlacementAndBoundsBehaviour.SupportQuery {
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
