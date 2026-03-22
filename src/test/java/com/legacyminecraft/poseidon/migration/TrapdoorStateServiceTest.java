package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.TrapdoorStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TrapdoorStateServiceTest {
    @Test
    public void openBitAndToggleRulesMatchLegacyDataEncoding() {
        TrapdoorStateBehaviour service = TrapdoorStateBehaviour.getInstance();

        Assert.assertFalse(service.isOpen(0));
        Assert.assertTrue(service.isOpen(4));
        Assert.assertEquals(4, service.toggleOpenBit(0));
        Assert.assertEquals(0, service.toggleOpenBit(4));
        Assert.assertTrue(service.shouldToggleOpenState(0, true));
        Assert.assertFalse(service.shouldToggleOpenState(4, true));
    }

    @Test
    public void boundsRulesMatchLegacyClosedAndOpenLayouts() {
        TrapdoorStateBehaviour service = TrapdoorStateBehaviour.getInstance();

        TrapdoorStateBehaviour.Bounds closed = service.resolveBounds(0);
        Assert.assertEquals(0.1875F, closed.getMaxY(), 0.0F);

        TrapdoorStateBehaviour.Bounds openFacingTwo = service.resolveBounds(6);
        Assert.assertEquals(0.8125F, openFacingTwo.getMinX(), 0.0F);
        Assert.assertEquals(1.0F, openFacingTwo.getMaxX(), 0.0F);
    }

    @Test
    public void placementAndAttachmentRulesMatchLegacySideMappings() {
        TrapdoorStateBehaviour service = TrapdoorStateBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(10, 64, 11);
        support.add(11, 64, 10);

        Assert.assertFalse(service.canPlaceOnSide(support, 10, 64, 10, 0));
        Assert.assertFalse(service.canPlaceOnSide(support, 10, 64, 10, 1));
        Assert.assertTrue(service.canPlaceOnSide(support, 10, 64, 10, 2));
        Assert.assertTrue(service.canPlaceOnSide(support, 10, 64, 10, 4));

        Assert.assertEquals(0, service.resolvePostPlaceData(2));
        Assert.assertEquals(1, service.resolvePostPlaceData(3));
        Assert.assertEquals(2, service.resolvePostPlaceData(4));
        Assert.assertEquals(3, service.resolvePostPlaceData(5));

        TrapdoorStateBehaviour.AttachmentOffset facingZero = service.resolveAttachmentOffset(0);
        Assert.assertEquals(0, facingZero.getX());
        Assert.assertEquals(1, facingZero.getZ());

        TrapdoorStateBehaviour.AttachmentOffset facingThree = service.resolveAttachmentOffset(3);
        Assert.assertEquals(-1, facingThree.getX());
        Assert.assertEquals(0, facingThree.getZ());
    }

    private static final class StubSupportQuery implements TrapdoorStateBehaviour.SupportQuery {
        private final Set keys = new HashSet();

        public void add(int x, int y, int z) {
            keys.add(key(x, y, z));
        }

        public boolean isBlockSolid(int x, int y, int z) {
            return keys.contains(key(x, y, z));
        }

        private String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }
    }
}
