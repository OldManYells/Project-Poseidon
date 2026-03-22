package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FlowerStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class FlowerStateServiceTest {
    @Test
    public void placementAndSurvivalRulesMatchLegacyFlowerBehavior() {
        FlowerStateBehaviour service = FlowerStateBehaviour.getInstance();

        Assert.assertTrue(service.canPlantOn(2, 2, 3, 60));
        Assert.assertTrue(service.canPlantOn(3, 2, 3, 60));
        Assert.assertTrue(service.canPlantOn(60, 2, 3, 60));
        Assert.assertFalse(service.canPlantOn(1, 2, 3, 60));

        Assert.assertTrue(service.canPlace(true, true));
        Assert.assertFalse(service.canPlace(false, true));
        Assert.assertFalse(service.canPlace(true, false));

        Assert.assertTrue(service.canStay(8, false, true));
        Assert.assertTrue(service.canStay(4, true, true));
        Assert.assertFalse(service.canStay(4, false, true));
        Assert.assertFalse(service.canStay(9, true, false));
        Assert.assertTrue(service.shouldDropForInvalidPlacement(false));
        Assert.assertFalse(service.shouldDropForInvalidPlacement(true));
    }
}
