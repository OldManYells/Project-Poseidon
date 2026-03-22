package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PumpkinStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class PumpkinStateServiceTest {
    @Test
    public void textureRulesMatchLegacyFaceAndLitMappings() {
        PumpkinStateBehaviour service = PumpkinStateBehaviour.getInstance();

        Assert.assertEquals(86, service.resolveTextureBySideAndData(1, 0, 86, false));
        Assert.assertEquals(86, service.resolveTextureBySideAndData(0, 0, 86, false));
        Assert.assertEquals(103, service.resolveTextureBySideAndData(2, 2, 86, false));
        Assert.assertEquals(104, service.resolveTextureBySideAndData(2, 2, 86, true));
        Assert.assertEquals(102, service.resolveTextureBySideAndData(4, 3, 86, false));
        Assert.assertEquals(103, service.resolveTextureBySide(3, 86));
        Assert.assertEquals(102, service.resolveTextureBySide(2, 86));
    }

    @Test
    public void placementRulesMatchLegacyReplaceableSupportAndYawPolicy() {
        PumpkinStateBehaviour service = PumpkinStateBehaviour.getInstance();

        Assert.assertTrue(service.canPlace(true, true));
        Assert.assertFalse(service.canPlace(true, false));
        Assert.assertFalse(service.canPlace(false, true));

        Assert.assertEquals(2, service.resolvePlacementDataFromYaw(0.0F));
        Assert.assertEquals(3, service.resolvePlacementDataFromYaw(90.0F));
        Assert.assertEquals(0, service.resolvePlacementDataFromYaw(180.0F));
        Assert.assertEquals(1, service.resolvePlacementDataFromYaw(270.0F));
    }
}
