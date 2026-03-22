package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.ColumnPlantGrowthBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ColumnPlantGrowthServiceTest {
    @Test
    public void countContiguousBelowAndGrowthRulesMatchLegacyLogic() {
        ColumnPlantGrowthBehaviour service = ColumnPlantGrowthBehaviour.getInstance();
        StubBlockIdQuery query = new StubBlockIdQuery();
        query.put(10, 63, 10, 81);
        query.put(10, 62, 10, 81);

        Assert.assertEquals(3, service.countContiguousBelow(query, 10, 64, 10, 81));
        Assert.assertTrue(service.shouldAttemptGrowth(true, 2, 3));
        Assert.assertFalse(service.shouldAttemptGrowth(true, 3, 3));
        Assert.assertFalse(service.shouldAttemptGrowth(false, 2, 3));
        Assert.assertTrue(service.shouldSpawnNewSegment(15, 15));
        Assert.assertFalse(service.shouldSpawnNewSegment(14, 15));
        Assert.assertEquals(0, service.nextGrowthData(15, 15));
        Assert.assertEquals(8, service.nextGrowthData(7, 15));
    }

    private static final class StubBlockIdQuery implements ColumnPlantGrowthBehaviour.BlockIdQuery {
        private final Map types = new HashMap();

        public void put(int x, int y, int z, int typeId) {
            types.put(key(x, y, z), Integer.valueOf(typeId));
        }

        public int getTypeId(int x, int y, int z) {
            Integer type = (Integer) types.get(key(x, y, z));
            return type == null ? 0 : type.intValue();
        }

        private String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }
    }
}
