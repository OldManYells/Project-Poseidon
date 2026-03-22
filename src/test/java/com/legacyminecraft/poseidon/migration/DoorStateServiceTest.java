package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.DoorStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class DoorStateServiceTest {
    @Test
    public void textureIndexRulesMatchLegacyOrientationAndOpenBitBehavior() {
        DoorStateBehaviour service = DoorStateBehaviour.getInstance();

        Assert.assertEquals(97, service.resolveTextureIndex(0, 0, 97));
        Assert.assertEquals(97, service.resolveTextureIndex(1, 0, 97));
        Assert.assertEquals(97, service.resolveTextureIndex(2, 0, 97));
        Assert.assertEquals(-97, service.resolveTextureIndex(4, 2, 97));
    }

    @Test
    public void boundsAndOrientationRulesMatchLegacyMappings() {
        DoorStateBehaviour service = DoorStateBehaviour.getInstance();

        Assert.assertEquals(0, service.resolveBoundingOrientation(1));
        Assert.assertEquals(0, service.resolveBoundingOrientation(4));
        Assert.assertEquals(2, service.resolveBoundingOrientation(3));

        DoorStateBehaviour.Bounds orientationZero = service.resolveBounds(0);
        Assert.assertEquals(0.1875F, orientationZero.getMaxZ(), 0.0F);

        DoorStateBehaviour.Bounds invalidOrientation = service.resolveBounds(9);
        Assert.assertEquals(2.0F, invalidOrientation.getMaxY(), 0.0F);
    }

    @Test
    public void openAndUpperHalfBitRulesMatchLegacyEncoding() {
        DoorStateBehaviour service = DoorStateBehaviour.getInstance();

        Assert.assertFalse(service.isOpen(0));
        Assert.assertTrue(service.isOpen(4));
        Assert.assertFalse(service.isUpperHalf(0));
        Assert.assertTrue(service.isUpperHalf(8));
        Assert.assertEquals(4, service.toggleOpenBit(0));
        Assert.assertEquals(0, service.toggleOpenBit(4));
        Assert.assertEquals(12, service.resolveUpperDataFromLowerToggle(0));
        Assert.assertTrue(DoorStateBehaviour.isOpenStatic(4));
    }

    @Test
    public void placementDropAndStructureRulesMatchLegacyPolicy() {
        DoorStateBehaviour service = DoorStateBehaviour.getInstance();

        Assert.assertTrue(service.canPlace(10, true, true, true));
        Assert.assertFalse(service.canPlace(127, true, true, true));
        Assert.assertFalse(service.canPlace(10, false, true, true));

        Assert.assertEquals(0, service.resolveDropItemId(8, true, 330, 324));
        Assert.assertEquals(330, service.resolveDropItemId(0, true, 330, 324));
        Assert.assertEquals(324, service.resolveDropItemId(0, false, 330, 324));

        Assert.assertTrue(service.shouldRemoveUpperHalf(0, 64));
        Assert.assertFalse(service.shouldRemoveUpperHalf(64, 64));
        Assert.assertTrue(service.shouldRemoveLowerHalf(0, true, 64));
        Assert.assertTrue(service.shouldRemoveLowerHalf(64, false, 64));
        Assert.assertTrue(service.shouldAlsoRemoveUpperHalfWhenLowerRemoved(true, 64, 64));
        Assert.assertFalse(service.shouldAlsoRemoveUpperHalfWhenLowerRemoved(false, 64, 64));
    }
}
