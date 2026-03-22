package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonExtensionGeometryBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class PistonExtensionGeometryServiceTest {
    @Test
    public void extractFacingReadsLowerThreeMetadataBits() {
        PistonExtensionGeometryBehaviour service = PistonExtensionGeometryBehaviour.getInstance();

        Assert.assertEquals(0, service.extractFacing(8));
        Assert.assertEquals(3, service.extractFacing(11));
        Assert.assertEquals(5, service.extractFacing(13));
    }

    @Test
    public void resolveTextureIndexMatchesLegacyFacingAndExtensionRules() {
        PistonExtensionGeometryBehaviour service = PistonExtensionGeometryBehaviour.getInstance();

        Assert.assertEquals(42, service.resolveTextureIndex(2, 2, 42, 100));
        Assert.assertEquals(99, service.resolveTextureIndex(2, 10, -1, 100));
        Assert.assertEquals(107, service.resolveTextureIndex(3, 2, -1, 100));
        Assert.assertEquals(108, service.resolveTextureIndex(4, 2, -1, 100));
    }

    @Test
    public void resolveCollisionShapesMatchesLegacySwitchLayouts() {
        PistonExtensionGeometryBehaviour service = PistonExtensionGeometryBehaviour.getInstance();
        PistonExtensionGeometryBehaviour.CollisionShapePair facingZero = service.resolveCollisionShapes(0);

        Assert.assertNotNull(facingZero);
        Assert.assertEquals(0.25F, facingZero.getPrimary().getMaxY(), 0.0F);
        Assert.assertEquals(0.375F, facingZero.getSecondary().getMinX(), 0.0F);
        Assert.assertEquals(1.0F, facingZero.getSecondary().getMaxY(), 0.0F);

        PistonExtensionGeometryBehaviour.CollisionShapePair facingFive = service.resolveCollisionShapes(5);
        Assert.assertNotNull(facingFive);
        Assert.assertEquals(0.75F, facingFive.getPrimary().getMinX(), 0.0F);
        Assert.assertEquals(0.0F, facingFive.getSecondary().getMinX(), 0.0F);

        Assert.assertNull(service.resolveCollisionShapes(7));
    }

    @Test
    public void resolveOutlineShapeMatchesLegacySwitchLayouts() {
        PistonExtensionGeometryBehaviour service = PistonExtensionGeometryBehaviour.getInstance();

        PistonExtensionGeometryBehaviour.Bounds facingThree = service.resolveOutlineShape(3);
        Assert.assertNotNull(facingThree);
        Assert.assertEquals(0.75F, facingThree.getMinZ(), 0.0F);
        Assert.assertEquals(1.0F, facingThree.getMaxY(), 0.0F);

        Assert.assertNull(service.resolveOutlineShape(7));
    }
}
