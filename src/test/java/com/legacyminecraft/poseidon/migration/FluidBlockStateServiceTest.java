package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FluidBlockStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FluidBlockStateServiceTest {
    @Test
    public void fluidHeightTextureCollisionAndDropRulesMatchLegacyBehavior() {
        FluidBlockStateBehaviour service = FluidBlockStateBehaviour.getInstance();

        Assert.assertEquals(1.0F / 9.0F, service.normalizedFluidHeight(0), 0.0F);
        Assert.assertEquals(1.0F / 9.0F, service.normalizedFluidHeight(8), 0.0F);
        Assert.assertEquals(14, service.resolveTextureBySide(0, 14));
        Assert.assertEquals(15, service.resolveTextureBySide(2, 14));
        Assert.assertEquals(-1, service.resolveFlowData(false, 4));
        Assert.assertEquals(0, service.resolveFlowData(true, 12));
        Assert.assertEquals(12, service.resolveRawFlowData(true, 12));
        Assert.assertEquals(-1, service.resolveRawFlowData(false, 12));
        Assert.assertTrue(service.canCollideCheck(0, true));
        Assert.assertFalse(service.canCollideCheck(1, true));
        Assert.assertFalse(service.canCollideCheck(0, false));
        Assert.assertFalse(service.shouldRenderSide(true, false, 1, true));
        Assert.assertFalse(service.shouldRenderSide(false, true, 1, true));
        Assert.assertTrue(service.shouldRenderSide(false, false, 1, false));
        Assert.assertTrue(service.shouldRenderSide(false, false, 2, true));
        Assert.assertFalse(service.shouldRenderSide(false, false,2, false));
        Assert.assertEquals(0, service.resolveDroppedItemId());
        Assert.assertEquals(0, service.resolveDroppedCount());
        Assert.assertFalse(service.isOpaqueCube());
        Assert.assertFalse(service.isNormalCube());
    }

    @Test
    public void flowVectorTickDelayAndLavaMixRulesMatchLegacyBehavior() {
        FluidBlockStateBehaviour service = FluidBlockStateBehaviour.getInstance();

        final Map<String, Integer> flowData = new HashMap<String, Integer>();
        final Map<String, Integer> blockData = new HashMap<String, Integer>();

        flowData.put(key(0, 0, 0), 3);
        flowData.put(key(1, 0, 0), 1);
        blockData.put(key(0, 0, 0), 0);

        FluidBlockStateBehaviour.FlowVector horizontal = service.computeFlowVector(new FluidBlockStateBehaviour.FlowQuery() {
            public int flowDataAt(int x, int y, int z) {
                Integer value = flowData.get(key(x, y, z));
                return value == null ? -1 : value;
            }

            public boolean isSolidMaterial(int x, int y, int z) {
                return true;
            }

            public int blockDataAt(int x, int y, int z) {
                Integer value = blockData.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public boolean canFlowOutside(int x, int y, int z, int side) {
                return false;
            }
        }, 0, 0, 0);

        Assert.assertEquals(-1.0D, horizontal.x, 0.0001D);
        Assert.assertEquals(0.0D, horizontal.y, 0.0001D);
        Assert.assertEquals(0.0D, horizontal.z, 0.0001D);

        flowData.remove(key(1, 0, 0));
        blockData.put(key(0, 0, 0), 8);
        FluidBlockStateBehaviour.FlowVector downward = service.computeFlowVector(new FluidBlockStateBehaviour.FlowQuery() {
            public int flowDataAt(int x, int y, int z) {
                Integer value = flowData.get(key(x, y, z));
                return value == null ? -1 : value;
            }

            public boolean isSolidMaterial(int x, int y, int z) {
                return true;
            }

            public int blockDataAt(int x, int y, int z) {
                Integer value = blockData.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public boolean canFlowOutside(int x, int y, int z, int side) {
                return x == 0 && y == 0 && z == -1 && side == 2;
            }
        }, 0, 0, 0);

        Assert.assertEquals(0.0D, downward.x, 0.0001D);
        Assert.assertEquals(-1.0D, downward.y, 0.0001D);
        Assert.assertEquals(0.0D, downward.z, 0.0001D);

        Assert.assertEquals(5, service.resolveTickDelay(true, false));
        Assert.assertEquals(30, service.resolveTickDelay(false, true));
        Assert.assertEquals(0, service.resolveTickDelay(false, false));
        Assert.assertTrue(service.shouldProcessLavaMix(11, 11, true));
        Assert.assertFalse(service.shouldProcessLavaMix(10, 11, true));
        Assert.assertFalse(service.shouldProcessLavaMix(11, 11, false));
        Assert.assertTrue(service.hasWaterNeighbor(new FluidBlockStateBehaviour.MaterialQuery() {
            public boolean isWater(int x, int y, int z) {
                return x == 1 && y == 2 && z == 2;
            }
        }, 2, 2, 2));
        Assert.assertFalse(service.hasWaterNeighbor(new FluidBlockStateBehaviour.MaterialQuery() {
            public boolean isWater(int x, int y, int z) {
                return false;
            }
        }, 2, 2, 2));
        Assert.assertEquals(49, service.resolveLavaMixResult(0, 49, 4));
        Assert.assertEquals(4, service.resolveLavaMixResult(4, 49, 4));
        Assert.assertEquals(-1, service.resolveLavaMixResult(5, 49, 4));
        Assert.assertEquals(2.6F, service.resolveFizzPitch(0.0F), 0.0F);
        Assert.assertEquals(8, service.resolveFizzSmokeCount());
    }

    private static String key(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }
}
