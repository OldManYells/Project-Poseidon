package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonMovingBlockBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityPiston;
import org.junit.Assert;
import org.junit.Test;

public class PistonMovingBlockServiceTest {
    @Test
    public void createMovingTileEntityPreservesLegacyConstructorArguments() {
        PistonMovingBlockBehaviour service = PistonMovingBlockBehaviour.getInstance();

        TileEntity tileEntity = service.createMovingTileEntity(12, 3, 5, true, false);
        Assert.assertTrue(tileEntity instanceof TileEntityPiston);

        TileEntityPiston piston = (TileEntityPiston) tileEntity;
        Assert.assertEquals(12, piston.a());
        Assert.assertEquals(3, piston.e());
        Assert.assertEquals(5, piston.d());
        Assert.assertTrue(piston.c());
    }

    @Test
    public void extractPistonTileEntityFiltersByConcreteType() {
        PistonMovingBlockBehaviour service = PistonMovingBlockBehaviour.getInstance();

        Assert.assertNull(service.extractPistonTileEntity(new TileEntity()));
        Assert.assertNotNull(service.extractPistonTileEntity(new TileEntityPiston(1, 2, 3, false, true)));
    }

    @Test
    public void resolveRenderProgressMatchesLegacyExtendingAndRetractingRules() {
        PistonMovingBlockBehaviour service = PistonMovingBlockBehaviour.getInstance();

        Assert.assertEquals(1.0F, service.resolveRenderProgress(new TileEntityPiston(1, 0, 0, true, true)), 0.0F);
        Assert.assertEquals(0.0F, service.resolveRenderProgress(new TileEntityPiston(1, 0, 0, false, true)), 0.0F);
    }

    @Test
    public void resolveShiftedCollisionBoxReturnsNullForAirOrSelfBlock() {
        PistonMovingBlockBehaviour service = PistonMovingBlockBehaviour.getInstance();

        Assert.assertNull(service.resolveShiftedCollisionBox(null, 0, 0, 0, 0, 0.5F, 5, Block.PISTON_MOVING.id));
        Assert.assertNull(service.resolveShiftedCollisionBox(null, 0, 0, 0, Block.PISTON_MOVING.id, 0.5F, 5, Block.PISTON_MOVING.id));
    }

    @Test
    public void resolveShiftedOutlineBoundsAppliesFacingOffset() {
        PistonMovingBlockBehaviour service = PistonMovingBlockBehaviour.getInstance();
        PistonMovingBlockBehaviour.Bounds bounds = service.resolveShiftedOutlineBounds(Block.STONE, 0.5F, 5);

        Assert.assertEquals(-0.5D, bounds.getMinX(), 0.0D);
        Assert.assertEquals(0.5D, bounds.getMaxX(), 0.0D);
        Assert.assertEquals(0.0D, bounds.getMinY(), 0.0D);
        Assert.assertEquals(1.0D, bounds.getMaxY(), 0.0D);
    }
}
