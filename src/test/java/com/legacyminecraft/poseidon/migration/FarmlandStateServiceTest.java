package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FarmlandStateBehaviour;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Material;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FarmlandStateServiceTest {
    @Test
    public void textureMoistureAndCollisionRulesMatchLegacyBehavior() {
        FarmlandStateBehaviour service = FarmlandStateBehaviour.getInstance();
        AxisAlignedBB box = service.resolveCollisionBox(10, 64, 10);

        Assert.assertEquals(10.0D, box.a, 0.0D);
        Assert.assertEquals(65.0D, box.e, 0.0D);
        Assert.assertEquals(86, service.resolveTextureBySideAndMoisture(1, 7, 87, 2));
        Assert.assertEquals(87, service.resolveTextureBySideAndMoisture(1, 0, 87, 2));
        Assert.assertEquals(2, service.resolveTextureBySideAndMoisture(3, 7, 87, 2));
        Assert.assertEquals(7, service.hydratedMoisture());
        Assert.assertEquals(2, service.resolveNextMoisture(3));
    }

    @Test
    public void hydrationCropAndTrampleRulesMatchLegacyBehavior() {
        FarmlandStateBehaviour service = FarmlandStateBehaviour.getInstance();

        Assert.assertTrue(service.shouldDecayWithoutWaterAndRain(false, false));
        Assert.assertFalse(service.shouldDecayWithoutWaterAndRain(true, false));
        Assert.assertFalse(service.shouldDecayWithoutWaterAndRain(false, true));
        Assert.assertTrue(service.shouldTurnToDirtWhenDry(0, false));
        Assert.assertFalse(service.shouldTurnToDirtWhenDry(0, true));
        Assert.assertTrue(service.shouldTurnToDirtForBlockAbove(true));
        Assert.assertFalse(service.shouldTurnToDirtForBlockAbove(false));

        Assert.assertTrue(service.hasCropsAbove(new FarmlandStateBehaviour.TypeQuery() {
            public int getTypeId(int x, int y, int z) {
                return x == 5 && y == 11 && z == 5 ? 59 : 0;
            }
        }, 5, 10, 5, 59));

        Assert.assertTrue(service.hasNearbyWater(new FarmlandStateBehaviour.MaterialQuery() {
            public Material getMaterial(int x, int y, int z) {
                return x == 9 && y == 11 && z == 1 ? Material.WATER : Material.AIR;
            }
        }, 5, 10, 5));

        Assert.assertFalse(service.hasNearbyWater(new FarmlandStateBehaviour.MaterialQuery() {
            public Material getMaterial(int x, int y, int z) {
                return Material.AIR;
            }
        }, 5, 10, 5));

        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        Assert.assertTrue(service.shouldProcessMoistureTick(deterministic));
        Assert.assertTrue(service.shouldTrampleToDirt(deterministic));
    }
}
