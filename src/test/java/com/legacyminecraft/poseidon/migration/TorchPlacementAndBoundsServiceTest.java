package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.TorchPlacementAndBoundsBehaviour;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TorchPlacementAndBoundsServiceTest {
    @Test
    public void supportAndPlacementRulesMatchLegacyBehavior() {
        TorchPlacementAndBoundsBehaviour service = TorchPlacementAndBoundsBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.addSolid(9, 64, 10);
        support.addType(10, 63, 10, Block.FENCE.id);

        Assert.assertTrue(service.hasFloorSupport(support, 10, 63, 10));
        Assert.assertTrue(service.canPlace(support, 10, 64, 10));
    }

    @Test
    public void postPlaceAndFallbackDataRulesMatchLegacyMappings() {
        TorchPlacementAndBoundsBehaviour service = TorchPlacementAndBoundsBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.addSolid(10, 64, 11);
        support.addSolid(11, 64, 10);
        support.addSolid(9, 64, 10);
        support.addType(10, 63, 10, Block.FENCE.id);

        Assert.assertEquals(4, service.resolvePostPlaceData(support, 0, 2, 10, 64, 10));
        Assert.assertEquals(2, service.resolvePostPlaceData(support, 0, 4, 10, 64, 10));
        Assert.assertEquals(1, service.resolveFallbackData(support, 10, 64, 10));

        StubSupportQuery floorOnly = new StubSupportQuery();
        floorOnly.addType(10, 63, 10, Block.FENCE.id);
        Assert.assertEquals(5, service.resolveFallbackData(floorOnly, 10, 64, 10));
    }

    @Test
    public void attachmentMissingAndBoundsRulesMatchLegacyCodePaths() {
        TorchPlacementAndBoundsBehaviour service = TorchPlacementAndBoundsBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();

        Assert.assertTrue(service.isAttachmentMissing(support, 10, 64, 10, 1));
        support.addSolid(9, 64, 10);
        Assert.assertFalse(service.isAttachmentMissing(support, 10, 64, 10, 1));

        TorchPlacementAndBoundsBehaviour.Bounds side = service.resolveRaytraceBounds(1);
        Assert.assertEquals(0.0F, side.getMinX(), 0.0F);
        Assert.assertEquals(0.8F, side.getMaxY(), 0.0F);

        TorchPlacementAndBoundsBehaviour.Bounds top = service.resolveRaytraceBounds(0);
        Assert.assertEquals(0.6F, top.getMaxY(), 0.0F);
    }

    @Test
    public void invalidPlacementDropPolicyMatchesLegacyFixGate() {
        TorchPlacementAndBoundsBehaviour service = TorchPlacementAndBoundsBehaviour.getInstance();

        Assert.assertFalse(service.shouldDropForInvalidPlacement(true, false, true));
        Assert.assertTrue(service.shouldDropForInvalidPlacement(false, false, false));
        Assert.assertTrue(service.shouldDropForInvalidPlacement(false, true, true));
        Assert.assertFalse(service.shouldDropForInvalidPlacement(false, true, false));
    }

    private static final class StubSupportQuery implements TorchPlacementAndBoundsBehaviour.SupportQuery {
        private final Set solidKeys = new HashSet();
        private final Map typeIds = new HashMap();

        public void addSolid(int x, int y, int z) {
            solidKeys.add(key(x, y, z));
        }

        public void addType(int x, int y, int z, int typeId) {
            typeIds.put(key(x, y, z), Integer.valueOf(typeId));
        }

        public boolean isBlockSolid(int x, int y, int z) {
            return solidKeys.contains(key(x, y, z));
        }

        public int getTypeId(int x, int y, int z) {
            Integer typeId = (Integer) typeIds.get(key(x, y, z));
            return typeId == null ? 0 : typeId.intValue();
        }

        private String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }
    }
}
