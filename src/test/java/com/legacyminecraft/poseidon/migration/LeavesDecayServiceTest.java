package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.LeavesDecayBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LeavesDecayServiceTest {
    @Test
    public void decayMarkingAndTickEvaluationMatchLegacyBehavior() {
        LeavesDecayBehaviour service = LeavesDecayBehaviour.getInstance();
        int leavesId = 18;
        int logId = 17;

        final int x = 100;
        final int y = 64;
        final int z = 100;

        final Map<String, Integer> typeIds = new HashMap<String, Integer>();
        final Map<String, Integer> data = new HashMap<String, Integer>();

        typeIds.put(key(x, y, z), leavesId);
        data.put(key(x, y, z), 8);
        typeIds.put(key(x, y, z + 1), leavesId);
        data.put(key(x, y, z + 1), 1);
        typeIds.put(key(x + 1, y, z), logId);

        service.markNearbyLeavesForDecay(new LeavesDecayBehaviour.RemoveQuery() {
            public boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                return true;
            }

            public int getTypeId(int qx, int qy, int qz) {
                Integer value = typeIds.get(key(qx, qy, qz));
                return value == null ? 0 : value;
            }

            public int getData(int qx, int qy, int qz) {
                Integer value = data.get(key(qx, qy, qz));
                return value == null ? 0 : value;
            }

            public void setRawData(int qx, int qy, int qz, int value) {
                data.put(key(qx, qy, qz), value);
            }
        }, x, y, z, leavesId);

        Assert.assertEquals(9, data.get(key(x, y, z + 1)).intValue());

        LeavesDecayBehaviour.DecayResult connected = service.evaluateDecayTick(new LeavesDecayBehaviour.DecayQuery() {
            public boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                return true;
            }

            public int getTypeId(int qx, int qy, int qz) {
                Integer value = typeIds.get(key(qx, qy, qz));
                return value == null ? 0 : value;
            }
        }, x, y, z, 8, null, leavesId, logId);

        Assert.assertNotNull(connected.scratch);
        Assert.assertTrue(connected.clearDecayBit);
        Assert.assertFalse(connected.decayNow);
        Assert.assertEquals(0, service.clearDecayBit(8));

        typeIds.remove(key(x + 1, y, z));
        LeavesDecayBehaviour.DecayResult disconnected = service.evaluateDecayTick(new LeavesDecayBehaviour.DecayQuery() {
            public boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                return true;
            }

            public int getTypeId(int qx, int qy, int qz) {
                Integer value = typeIds.get(key(qx, qy, qz));
                return value == null ? 0 : value;
            }
        }, x, y, z, 8, connected.scratch, leavesId, logId);

        Assert.assertFalse(disconnected.clearDecayBit);
        Assert.assertTrue(disconnected.decayNow);
    }

    @Test
    public void saplingShearVariantOpacityAndTextureRulesMatchLegacyBehavior() {
        LeavesDecayBehaviour service = LeavesDecayBehaviour.getInstance();

        Assert.assertEquals(1, service.resolveSaplingDropCount(new FixedRandom(0)));
        Assert.assertEquals(0, service.resolveSaplingDropCount(new FixedRandom(1)));
        Assert.assertEquals(6, service.resolveSaplingDropItemId(6));
        Assert.assertTrue(service.shouldUseShearHarvest(false, 359, 359));
        Assert.assertFalse(service.shouldUseShearHarvest(true, 359, 359));
        Assert.assertFalse(service.shouldUseShearHarvest(false, 0, 359));
        Assert.assertEquals(3, service.stripVariantData(11));
        Assert.assertTrue(service.isOpaqueCube(false));
        Assert.assertFalse(service.isOpaqueCube(true));
        Assert.assertEquals(132, service.resolveTextureByVariantData(1, 52));
        Assert.assertEquals(52, service.resolveTextureByVariantData(2, 52));
    }

    private static String key(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }

    private static final class FixedRandom extends Random {
        private final int fixed;

        private FixedRandom(int fixed) {
            this.fixed = fixed;
        }

        public int nextInt(int bound) {
            return this.fixed;
        }
    }
}
