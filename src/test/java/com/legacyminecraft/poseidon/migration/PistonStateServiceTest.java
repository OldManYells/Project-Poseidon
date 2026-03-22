package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class PistonStateServiceTest {
    @Test
    public void facingAndExtendedBitExtractionMatchesLegacyMetadataPolicy() {
        PistonStateBehaviour service = PistonStateBehaviour.getInstance();

        Assert.assertEquals(3, service.extractFacing(11));
        Assert.assertFalse(service.isExtended(3));
        Assert.assertTrue(service.isExtended(11));
    }

    @Test
    public void textureSelectionMatchesLegacyFacingAndExtensionRules() {
        PistonStateBehaviour service = PistonStateBehaviour.getInstance();

        Assert.assertEquals(100, service.resolveTextureIndex(2, 2, 100, true));
        Assert.assertEquals(110, service.resolveTextureIndex(2, 2, 100, false));
        Assert.assertEquals(109, service.resolveTextureIndex(3, 2, 100, true));
        Assert.assertEquals(108, service.resolveTextureIndex(4, 2, 100, true));
        Assert.assertEquals(100, service.resolveTextureIndex(0, 7, 100, true));
    }

    @Test
    public void placementFacingResolvesVerticalAndHorizontalDirectionsLikeLegacyCode() {
        PistonStateBehaviour service = PistonStateBehaviour.getInstance();

        Assert.assertEquals(1, service.resolvePlacedFacing(10.5D, 68.0D, 10.5D, 0.0F, 1.62F, 10, 64, 10));
        Assert.assertEquals(0, service.resolvePlacedFacing(10.5D, 62.0D, 10.5D, 0.0F, 1.62F, 10, 64, 10));
        Assert.assertEquals(2, service.resolvePlacedFacing(10.5D, 64.0D, 10.5D, 0.0F, 1.62F, 10, 64, 10));
        Assert.assertEquals(5, service.resolvePlacedFacing(10.5D, 64.0D, 10.5D, 90.0F, 1.62F, 10, 64, 10));
        Assert.assertEquals(3, service.resolvePlacedFacing(10.5D, 64.0D, 10.5D, 180.0F, 1.62F, 10, 64, 10));
        Assert.assertEquals(4, service.resolvePlacedFacing(10.5D, 64.0D, 10.5D, 270.0F, 1.62F, 10, 64, 10));
    }

    @Test
    public void extendedBoundsMatchLegacySwitchLayouts() {
        PistonStateBehaviour service = PistonStateBehaviour.getInstance();

        Assert.assertNull(service.resolveExtendedBounds(2));

        PistonStateBehaviour.Bounds facingZero = service.resolveExtendedBounds(8);
        Assert.assertNotNull(facingZero);
        Assert.assertEquals(0.25F, facingZero.getMinY(), 0.0F);

        PistonStateBehaviour.Bounds facingFour = service.resolveExtendedBounds(12);
        Assert.assertNotNull(facingFour);
        Assert.assertEquals(0.25F, facingFour.getMinX(), 0.0F);
    }
}
