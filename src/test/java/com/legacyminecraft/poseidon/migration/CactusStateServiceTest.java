package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.CactusStateBehaviour;
import net.minecraft.server.AxisAlignedBB;
import org.junit.Assert;
import org.junit.Test;

public class CactusStateServiceTest {
    @Test
    public void collisionTextureAndPlacementRulesMatchLegacyBehavior() {
        CactusStateBehaviour service = CactusStateBehaviour.getInstance();

        AxisAlignedBB box = service.resolveCollisionBox(10, 64, 10);
        Assert.assertEquals(10.0625D, box.a, 0.0D);
        Assert.assertEquals(64.9375D, box.e, 0.0D);
        Assert.assertEquals(94, service.resolveTextureBySide(1, 95));
        Assert.assertEquals(96, service.resolveTextureBySide(0, 95));
        Assert.assertEquals(95, service.resolveTextureBySide(3, 95));

        Assert.assertTrue(service.canRemainPlaced(false, false, false, false, 81, 81, 12));
        Assert.assertTrue(service.canRemainPlaced(false, false, false, false, 12, 81, 12));
        Assert.assertFalse(service.canRemainPlaced(true, false, false, false, 81, 81, 12));
        Assert.assertFalse(service.canRemainPlaced(false, false, false, false, 2, 81, 12));
        Assert.assertTrue(service.canPlace(true, true));
        Assert.assertFalse(service.canPlace(false, true));
        Assert.assertEquals(1, service.contactDamage());
    }
}
