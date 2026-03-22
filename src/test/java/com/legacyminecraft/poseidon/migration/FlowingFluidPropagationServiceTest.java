package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FlowingFluidPropagationBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FlowingFluidPropagationServiceTest {
    @Test
    public void levelComputationAndLavaSlowdownRulesMatchLegacyBehavior() {
        FlowingFluidPropagationBehaviour service = FlowingFluidPropagationBehaviour.getInstance();

        Assert.assertEquals(1, service.resolveFlowIncreaseStep(false, false));
        Assert.assertEquals(2, service.resolveFlowIncreaseStep(true, false));
        Assert.assertEquals(1, service.resolveFlowIncreaseStep(true, true));

        FlowingFluidPropagationBehaviour.LevelComputation waterLevel = service.computeNextLevel(new FlowingFluidPropagationBehaviour.LevelUpdateQuery() {
            public int fluidLevel(int x, int y, int z) {
                if (x == -1 && y == 64 && z == 10) {
                    return 0;
                }
                if (x == 1 && y == 64 && z == 10) {
                    return 0;
                }
                return -1;
            }

            public boolean belowIsBuildable(int x, int y, int z) {
                return true;
            }

            public boolean belowIsSameMaterial(int x, int y, int z) {
                return false;
            }

            public int currentData(int x, int y, int z) {
                return 0;
            }
        }, 0, 64, 10, 1, 1, true);
        Assert.assertEquals(0, waterLevel.nextLevel);
        Assert.assertEquals(2, waterLevel.sourceCount);

        FlowingFluidPropagationBehaviour.LevelComputation aboveLevel = service.computeNextLevel(new FlowingFluidPropagationBehaviour.LevelUpdateQuery() {
            public int fluidLevel(int x, int y, int z) {
                return y == 65 ? 4 : -1;
            }

            public boolean belowIsBuildable(int x, int y, int z) {
                return false;
            }

            public boolean belowIsSameMaterial(int x, int y, int z) {
                return false;
            }

            public int currentData(int x, int y, int z) {
                return 7;
            }
        }, 0, 64, 10, 7, 1, false);
        Assert.assertEquals(12, aboveLevel.nextLevel);

        FlowingFluidPropagationBehaviour.LavaSlowdownResult fixDisabled = service.applyLavaSlowdown(true, 3, 4, 1, false);
        Assert.assertEquals(3, fixDisabled.adjustedNextLevel);
        Assert.assertFalse(fixDisabled.shouldConvertToStill);
        FlowingFluidPropagationBehaviour.LavaSlowdownResult fixEnabled = service.applyLavaSlowdown(true, 3, 4, 1, true);
        Assert.assertEquals(4, fixEnabled.adjustedNextLevel);
        Assert.assertFalse(fixEnabled.shouldConvertToStill);

        Assert.assertEquals(10, service.resolveDownwardFlowLevel(2));
        Assert.assertEquals(8, service.resolveDownwardFlowLevel(8));
        Assert.assertEquals(3, service.resolveSideFlowLevel(2, 1));
        Assert.assertEquals(1, service.resolveSideFlowLevel(8, 2));
        Assert.assertEquals(-1, service.resolveSideFlowLevel(7, 1));
    }

    @Test
    public void slopeRoutingBlockingAndStillConversionRulesMatchLegacyBehavior() {
        FlowingFluidPropagationBehaviour service = FlowingFluidPropagationBehaviour.getInstance();
        AtomicInteger setCalls = new AtomicInteger();
        AtomicInteger markCalls = new AtomicInteger();
        AtomicInteger notifyCalls = new AtomicInteger();

        service.convertToStillBlock(new FlowingFluidPropagationBehaviour.ConvertToStillSink() {
            public void setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
                setCalls.incrementAndGet();
                Assert.assertEquals(11, typeId);
                Assert.assertEquals(3, data);
            }

            public void markNeighborsDirty(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                markCalls.incrementAndGet();
            }

            public void notifyBlock(int x, int y, int z) {
                notifyCalls.incrementAndGet();
            }
        }, 10, 64, 10, 10, 3);
        Assert.assertEquals(1, setCalls.get());
        Assert.assertEquals(1, markCalls.get());
        Assert.assertEquals(1, notifyCalls.get());

        int distance = service.computeSlopeDistance(new FlowingFluidPropagationBehaviour.SlopeQuery() {
            public boolean isBlocked(int x, int y, int z) {
                return false;
            }

            public boolean isSameMaterialLevelZero(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10, 1, -1);
        Assert.assertEquals(1, distance);

        final Map<String, Boolean> blocked = new HashMap<String, Boolean>();
        blocked.put(key(9, 64, 10), true);
        blocked.put(key(11, 64, 10), true);
        blocked.put(key(10, 64, 11), true);

        int[] costs = new int[4];
        boolean[] selected = new boolean[4];
        boolean[] result = service.resolveOptimalFlowDirections(new FlowingFluidPropagationBehaviour.DirectionQuery() {
            public boolean isBlocked(int x, int y, int z) {
                Boolean value = blocked.get(key(x, y, z));
                return value != null && value.booleanValue();
            }

            public boolean isSameMaterialLevelZero(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10, costs, selected);
        Assert.assertTrue(result[2]);
        Assert.assertFalse(result[0]);
        Assert.assertFalse(result[1]);
        Assert.assertFalse(result[3]);

        Assert.assertTrue(service.isBlockedType(64, false, 64, 71, 63, 65, 83));
        Assert.assertFalse(service.isBlockedType(0, false, 64, 71, 63, 65, 83));
        Assert.assertTrue(service.isBlockedType(1, true, 64, 71, 63, 65, 83));
        Assert.assertFalse(service.canFlowInto(true, false, false));
        Assert.assertFalse(service.canFlowInto(false, true, false));
        Assert.assertFalse(service.canFlowInto(false, false, true));
        Assert.assertTrue(service.canFlowInto(false, false, false));

        FlowingFluidPropagationBehaviour.NeighborLevelUpdate ignored = service.accumulateNeighbor(-1, 3);
        Assert.assertEquals(3, ignored.minLevel);
        Assert.assertEquals(0, ignored.sourceCountIncrement);
        FlowingFluidPropagationBehaviour.NeighborLevelUpdate source = service.accumulateNeighbor(0, -100);
        Assert.assertEquals(0, source.minLevel);
        Assert.assertEquals(1, source.sourceCountIncrement);
        FlowingFluidPropagationBehaviour.NeighborLevelUpdate normalized = service.accumulateNeighbor(9, 2);
        Assert.assertEquals(0, normalized.minLevel);
    }

    private static String key(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }
}
