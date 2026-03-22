package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SnowLayerStateBehaviour;
import net.minecraft.server.AxisAlignedBB;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SnowLayerStateServiceTest {
    @Test
    public void collisionAndSelectionBoundsMatchLegacyLayerRules() {
        SnowLayerStateBehaviour service = SnowLayerStateBehaviour.getInstance();

        AxisAlignedBB none = service.resolveCollisionBox(10, 64, 10, 2, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F);
        Assert.assertNull(none);

        AxisAlignedBB present = service.resolveCollisionBox(10, 64, 10, 3, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F);
        Assert.assertNotNull(present);
        Assert.assertEquals(10.0D, present.a, 0.0D);
        Assert.assertEquals(64.5D, present.e, 0.0D);
        Assert.assertEquals(11.0D, present.d, 0.0D);

        Assert.assertEquals(0.125F, service.resolveSelectionHeight(0), 0.0F);
        Assert.assertEquals(0.875F, service.resolveSelectionHeight(6), 0.0F);
    }

    @Test
    public void placementAndDropRulesMatchLegacySnowLayerBehavior() {
        SnowLayerStateBehaviour service = SnowLayerStateBehaviour.getInstance();

        Assert.assertTrue(service.canPlace(1, true, true));
        Assert.assertFalse(service.canPlace(0, true, true));
        Assert.assertFalse(service.canPlace(1, false, true));
        Assert.assertFalse(service.canPlace(1, true, false));

        Assert.assertEquals(332, service.resolveDropItemId(332));

        Random seeded = new Random(7L);
        double offset = service.resolveDropOffset(seeded, 0.7F);
        Assert.assertTrue(offset >= 0.15D);
        Assert.assertTrue(offset < 0.85D);
    }
}
