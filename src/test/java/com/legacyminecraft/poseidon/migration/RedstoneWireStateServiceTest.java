package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.RedstoneWireStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RedstoneWireStateServiceTest {
    @Test
    public void powerComputationAndPropagationRulesMatchLegacyBehavior() {
        RedstoneWireStateBehaviour service = RedstoneWireStateBehaviour.getInstance();

        Assert.assertTrue(service.canPlace(true));
        Assert.assertFalse(service.canPlace(false));
        Assert.assertEquals(15, service.computeTargetPower(new RedstoneWireStateBehaviour.PowerComputationQuery() {
            public int getPowerAt(int x, int y, int z, int currentMax) {
                return currentMax;
            }

            public boolean isSolidTop(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10, 0, 0, 0, true));

        int recomputed = service.computeTargetPower(new RedstoneWireStateBehaviour.PowerComputationQuery() {
            public int getPowerAt(int x, int y, int z, int currentMax) {
                if (x == 9 && y == 64 && z == 10) {
                    return 7;
                }
                return currentMax;
            }

            public boolean isSolidTop(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10, 0, 0, 0, false);
        Assert.assertEquals(6, recomputed);

        Assert.assertEquals(4, service.decayPower(5));
        Assert.assertEquals(0, service.decayPower(0));
        Assert.assertTrue(service.shouldPropagateNeighbor(4, 3));
        Assert.assertFalse(service.shouldPropagateNeighbor(-1, 3));
        Assert.assertFalse(service.shouldPropagateNeighbor(3, 3));

        AtomicInteger propagationCount = new AtomicInteger();
        service.forEachPropagationTarget(new RedstoneWireStateBehaviour.PropagationQuery() {
            public int getPowerAt(int x, int y, int z, int currentMax) {
                return x == 10 && y == 64 && z == 9 ? 6 : -1;
            }

            public int currentPower(int x, int y, int z) {
                return 5;
            }

            public boolean isSolidTop(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10, new RedstoneWireStateBehaviour.PositionConsumer() {
            public void accept(int x, int y, int z) {
                propagationCount.incrementAndGet();
            }
        });
        Assert.assertEquals(1, propagationCount.get());
    }

    @Test
    public void neighborCoordinateAndConnectionRulesMatchLegacyBehavior() {
        RedstoneWireStateBehaviour service = RedstoneWireStateBehaviour.getInstance();

        AtomicInteger transitionCount = new AtomicInteger();
        service.forEachTransitionPhysicsPosition(10, 64, 10, new RedstoneWireStateBehaviour.PositionConsumer() {
            public void accept(int x, int y, int z) {
                transitionCount.incrementAndGet();
            }
        });
        Assert.assertEquals(7, transitionCount.get());

        AtomicInteger extendedCount = new AtomicInteger();
        service.forEachExtendedNeighborPosition(new RedstoneWireStateBehaviour.SolidQuery() {
            public boolean isSolidTop(int x, int y, int z) {
                return x == 9 && y == 64 && z == 10;
            }
        }, 10, 64, 10, new RedstoneWireStateBehaviour.PositionConsumer() {
            public void accept(int x, int y, int z) {
                extendedCount.incrementAndGet();
            }
        });
        Assert.assertEquals(8, extendedCount.get());

        final Map<String, Integer> types = new HashMap<String, Integer>();
        final Map<String, Integer> data = new HashMap<String, Integer>();
        types.put(key(10, 64, 10), 55);
        types.put(key(11, 64, 10), 93);
        data.put(key(11, 64, 10), 2);
        types.put(key(12, 64, 10), 1);

        Assert.assertTrue(service.isWireOrPowerSourceConnection(new RedstoneWireStateBehaviour.StaticConnectionQuery() {
            public int typeIdAt(int x, int y, int z) {
                Integer value = types.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public int dataAt(int x, int y, int z) {
                Integer value = data.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public boolean isPowerSource(int typeId) {
                return typeId == 1;
            }
        }, 10, 64, 10, 1, 55, 93, 94, new int[]{0, 1, 2, 3}));
        Assert.assertTrue(service.isWireOrPowerSourceConnection(new RedstoneWireStateBehaviour.StaticConnectionQuery() {
            public int typeIdAt(int x, int y, int z) {
                Integer value = types.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public int dataAt(int x, int y, int z) {
                Integer value = data.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public boolean isPowerSource(int typeId) {
                return typeId == 1;
            }
        }, 12, 64, 10, 1, 55, 93, 94, new int[]{0, 1, 2, 3}));
        Assert.assertTrue(service.isWireOrPowerSourceConnection(new RedstoneWireStateBehaviour.StaticConnectionQuery() {
            public int typeIdAt(int x, int y, int z) {
                Integer value = types.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public int dataAt(int x, int y, int z) {
                Integer value = data.get(key(x, y, z));
                return value == null ? 0 : value;
            }

            public boolean isPowerSource(int typeId) {
                return typeId == 1;
            }
        }, 11, 64, 10, 2, 55, 93, 94, new int[]{0, 1, 2, 3}));
        Assert.assertFalse(service.isWireOrPowerSourceConnection(new RedstoneWireStateBehaviour.StaticConnectionQuery() {
            public int typeIdAt(int x, int y, int z) {
                return 0;
            }

            public int dataAt(int x, int y, int z) {
                return 0;
            }

            public boolean isPowerSource(int typeId) {
                return false;
            }
        }, 1, 2, 3, 4, 55, 93, 94, new int[]{0, 1, 2, 3}));
    }

    private static String key(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }
}
