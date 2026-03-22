package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SignStateBehaviour;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntitySign;
import org.junit.Assert;
import org.junit.Test;

public class SignStateServiceTest {
    @Test
    public void wallBoundsAndAttachmentRulesMatchLegacySignBehavior() {
        SignStateBehaviour service = SignStateBehaviour.getInstance();

        SignStateBehaviour.Bounds full = service.fullBounds();
        Assert.assertEquals(0.0F, full.minX, 0.0F);
        Assert.assertEquals(1.0F, full.maxZ, 0.0F);

        SignStateBehaviour.Bounds north = service.resolveWallBounds(2);
        Assert.assertEquals(0.875F, north.minZ, 0.0F);
        Assert.assertEquals(1.0F, north.maxZ, 0.0F);
        SignStateBehaviour.Bounds south = service.resolveWallBounds(3);
        Assert.assertEquals(0.0F, south.minZ, 0.0F);
        Assert.assertEquals(0.125F, south.maxZ, 0.0F);
        SignStateBehaviour.Bounds east = service.resolveWallBounds(4);
        Assert.assertEquals(0.875F, east.minX, 0.0F);
        Assert.assertEquals(1.0F, east.maxX, 0.0F);
        SignStateBehaviour.Bounds west = service.resolveWallBounds(5);
        Assert.assertEquals(0.0F, west.minX, 0.0F);
        Assert.assertEquals(0.125F, west.maxX, 0.0F);

        Assert.assertTrue(service.shouldDropStandingSign(false));
        Assert.assertFalse(service.shouldDropStandingSign(true));
        Assert.assertTrue(service.hasValidWallAttachment(2, true, false, false, false));
        Assert.assertTrue(service.hasValidWallAttachment(3, false, true, false, false));
        Assert.assertTrue(service.hasValidWallAttachment(4, false, false, true, false));
        Assert.assertTrue(service.hasValidWallAttachment(5, false, false, false, true));
        Assert.assertFalse(service.hasValidWallAttachment(2, false, false, false, false));
        Assert.assertTrue(service.shouldDropWallSign(false));
        Assert.assertFalse(service.shouldDropWallSign(true));
    }

    @Test
    public void tileEntityDropAndRedstoneRulesMatchLegacySignBehavior() {
        SignStateBehaviour service = SignStateBehaviour.getInstance();

        TileEntity tileEntity = service.instantiateTileEntity(TileEntitySign.class);
        Assert.assertTrue(tileEntity instanceof TileEntitySign);
        Assert.assertEquals(323, service.resolveDropItemId(323));
        Assert.assertTrue(service.shouldFireRedstoneNeighborEvent(1, true));
        Assert.assertFalse(service.shouldFireRedstoneNeighborEvent(1, false));
    }
}
