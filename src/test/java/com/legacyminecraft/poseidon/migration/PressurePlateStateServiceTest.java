package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PressurePlateStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PressurePlateStateServiceTest {
    @Test
    public void supportAndReevaluationRulesMatchLegacyBehavior() {
        PressurePlateStateBehaviour service = PressurePlateStateBehaviour.getInstance();
        StubSupportQuery support = new StubSupportQuery();
        support.add(10, 63, 10);

        Assert.assertTrue(service.hasSupportBelow(support, 10, 64, 10));
        Assert.assertFalse(service.shouldDropWithoutSupport(support, 10, 64, 10));
        Assert.assertTrue(service.shouldReevaluateFromScheduledTick(1));
        Assert.assertFalse(service.shouldReevaluateFromScheduledTick(0));
        Assert.assertTrue(service.shouldReevaluateFromEntityTouch(0));
        Assert.assertFalse(service.shouldReevaluateFromEntityTouch(1));
    }

    @Test
    public void detectionAndVisualBoundsMatchLegacyDimensions() {
        PressurePlateStateBehaviour service = PressurePlateStateBehaviour.getInstance();

        Assert.assertEquals(10.125D, service.createDetectionBox(10, 64, 10, 0.125F).a, 0.0D);
        Assert.assertEquals(64.25D, service.createDetectionBox(10, 64, 10, 0.125F).e, 0.0D);

        PressurePlateStateBehaviour.Bounds unpowered = service.resolveVisualBounds(0);
        Assert.assertEquals(0.0625F, unpowered.getMaxY(), 0.0F);

        PressurePlateStateBehaviour.Bounds powered = service.resolveVisualBounds(1);
        Assert.assertEquals(0.03125F, powered.getMaxY(), 0.0F);
    }

    @Test
    public void powerStateAndSideRulesMatchLegacyMapping() {
        PressurePlateStateBehaviour service = PressurePlateStateBehaviour.getInstance();

        Assert.assertFalse(service.hasTriggeringEntities(Collections.EMPTY_LIST));
        Assert.assertTrue(service.hasTriggeringEntities(Collections.singletonList("entity")));
        Assert.assertEquals(1, service.toLegacyData(true));
        Assert.assertEquals(0, service.toLegacyData(false));
        Assert.assertTrue(service.isPowered(1));
        Assert.assertFalse(service.isPowered(0));
        Assert.assertTrue(service.isPoweringSide(1, 1));
        Assert.assertFalse(service.isPoweringSide(1, 2));
    }

    private static final class StubSupportQuery implements PressurePlateStateBehaviour.SupportQuery {
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
