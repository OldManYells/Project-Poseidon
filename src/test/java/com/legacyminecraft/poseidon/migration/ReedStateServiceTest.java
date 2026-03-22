package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.ReedStateBehaviour;
import net.minecraft.server.Material;
import org.junit.Assert;
import org.junit.Test;

public class ReedStateServiceTest {
    @Test
    public void placementAndDropRulesMatchLegacyBehavior() {
        ReedStateBehaviour service = ReedStateBehaviour.getInstance();

        Assert.assertTrue(service.canRemainPlaced(
                83, 83, 2, 3, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.WATER
        ));

        Assert.assertTrue(service.canRemainPlaced(
                2, 83, 2, 3, Material.WATER, Material.AIR, Material.AIR, Material.AIR, Material.WATER
        ));

        Assert.assertFalse(service.canRemainPlaced(
                2, 83, 2, 3, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.WATER
        ));

        Assert.assertFalse(service.canRemainPlaced(
                1, 83, 2, 3, Material.WATER, Material.AIR, Material.AIR, Material.AIR, Material.WATER
        ));

        Assert.assertEquals(338, service.dropItemId(338));
    }
}
