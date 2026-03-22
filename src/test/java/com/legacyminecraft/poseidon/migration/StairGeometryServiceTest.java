package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.StairGeometryBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class StairGeometryServiceTest {
    @Test
    public void resolveCollisionShapesMatchesLegacyMetadataLayouts() {
        StairGeometryBehaviour service = StairGeometryBehaviour.getInstance();

        StairGeometryBehaviour.CollisionShapePair metadataZero = service.resolveCollisionShapes(0);
        Assert.assertNotNull(metadataZero);
        Assert.assertEquals(0.5F, metadataZero.getPrimary().getMaxX(), 0.0F);
        Assert.assertEquals(0.5F, metadataZero.getPrimary().getMaxY(), 0.0F);
        Assert.assertEquals(1.0F, metadataZero.getSecondary().getMaxY(), 0.0F);

        StairGeometryBehaviour.CollisionShapePair metadataThree = service.resolveCollisionShapes(3);
        Assert.assertNotNull(metadataThree);
        Assert.assertEquals(0.5F, metadataThree.getPrimary().getMaxZ(), 0.0F);
        Assert.assertEquals(0.5F, metadataThree.getSecondary().getMaxY(), 0.0F);

        Assert.assertNull(service.resolveCollisionShapes(99));
    }

    @Test
    public void resolvePlacementMetadataMatchesLegacyYawMapping() {
        StairGeometryBehaviour service = StairGeometryBehaviour.getInstance();

        Assert.assertEquals(2, service.resolvePlacementMetadata(0.0F));
        Assert.assertEquals(1, service.resolvePlacementMetadata(90.0F));
        Assert.assertEquals(3, service.resolvePlacementMetadata(180.0F));
        Assert.assertEquals(0, service.resolvePlacementMetadata(270.0F));
    }
}
