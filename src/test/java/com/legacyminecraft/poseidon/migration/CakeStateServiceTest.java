package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.CakeStateBehaviour;
import net.minecraft.server.AxisAlignedBB;
import org.junit.Assert;
import org.junit.Test;

public class CakeStateServiceTest {
    @Test
    public void selectionBoundsMatchLegacyBiteProgression() {
        CakeStateBehaviour service = CakeStateBehaviour.getInstance();

        CakeStateBehaviour.Bounds fresh = service.resolveSelectionBounds(0);
        Assert.assertEquals(0.0625F, fresh.getMinX(), 0.0F);
        Assert.assertEquals(0.5F, fresh.getMaxY(), 0.0F);

        CakeStateBehaviour.Bounds mostlyEaten = service.resolveSelectionBounds(5);
        Assert.assertEquals(0.6875F, mostlyEaten.getMinX(), 0.0F);
        Assert.assertEquals(0.9375F, mostlyEaten.getMaxX(), 0.0F);
    }

    @Test
    public void collisionBoxAndTextureRulesMatchLegacyPolicy() {
        CakeStateBehaviour service = CakeStateBehaviour.getInstance();

        AxisAlignedBB box = service.resolveCollisionBox(10, 64, 10, 2);
        Assert.assertEquals(10.3125D, box.a, 0.0D);
        Assert.assertEquals(64.4375D, box.e, 0.0D);

        Assert.assertEquals(90, service.resolveTextureBySideAndBites(1, 3, 90));
        Assert.assertEquals(93, service.resolveTextureBySideAndBites(0, 3, 90));
        Assert.assertEquals(92, service.resolveTextureBySideAndBites(4, 1, 90));
        Assert.assertEquals(91, service.resolveTextureBySideAndBites(3, 1, 90));
        Assert.assertEquals(91, service.resolveTextureBySide(3, 90));
    }

    @Test
    public void eatingAndPlacementRulesMatchLegacyBehavior() {
        CakeStateBehaviour service = CakeStateBehaviour.getInstance();

        Assert.assertTrue(service.canEatAtHealth(19));
        Assert.assertFalse(service.canEatAtHealth(20));
        Assert.assertEquals(3, service.healAmountPerBite());
        Assert.assertEquals(3, service.incrementBites(2));
        Assert.assertTrue(service.isConsumed(6));
        Assert.assertFalse(service.isConsumed(5));

        Assert.assertTrue(service.canRemainPlaced(true, true));
        Assert.assertFalse(service.canRemainPlaced(false, true));
        Assert.assertFalse(service.canRemainPlaced(true, false));
        Assert.assertTrue(service.hasBuildableSupportBelow(true));
        Assert.assertFalse(service.hasBuildableSupportBelow(false));
    }
}
