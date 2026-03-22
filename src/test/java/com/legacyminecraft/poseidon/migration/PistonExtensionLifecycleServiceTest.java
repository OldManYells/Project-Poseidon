package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonExtensionLifecycleBehaviour;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

public class PistonExtensionLifecycleServiceTest {
    @Test
    public void validateLegacyExtensionDataRules() {
        PistonExtensionLifecycleBehaviour service = PistonExtensionLifecycleBehaviour.getInstance();

        Assert.assertTrue(service.isValidExtensionDataForRemoval(0));
        Assert.assertTrue(service.isValidExtensionDataForRemoval(13));
        Assert.assertFalse(service.isValidExtensionDataForRemoval(-1));
        Assert.assertFalse(service.isValidExtensionDataForRemoval(6));
        Assert.assertFalse(service.isValidExtensionDataForRemoval(7));
        Assert.assertFalse(service.isValidExtensionDataForRemoval(14));
    }

    @Test
    public void resolvePistonBaseForRemovalMatchesLegacyReverseFacingOffsets() {
        PistonExtensionLifecycleBehaviour service = PistonExtensionLifecycleBehaviour.getInstance();

        PistonExtensionLifecycleBehaviour.BasePosition forFacingTwo = service.resolvePistonBaseForRemoval(10, 20, 30, 2);
        Assert.assertNotNull(forFacingTwo);
        Assert.assertEquals(10, forFacingTwo.getX());
        Assert.assertEquals(20, forFacingTwo.getY());
        Assert.assertEquals(31, forFacingTwo.getZ());

        PistonExtensionLifecycleBehaviour.BasePosition forFacingFive = service.resolvePistonBaseForRemoval(10, 20, 30, 5);
        Assert.assertNotNull(forFacingFive);
        Assert.assertEquals(9, forFacingFive.getX());
        Assert.assertEquals(20, forFacingFive.getY());
        Assert.assertEquals(30, forFacingFive.getZ());
    }

    @Test
    public void resolvePistonBaseForPhysicsMatchesLegacyFacingOffsets() {
        PistonExtensionLifecycleBehaviour service = PistonExtensionLifecycleBehaviour.getInstance();

        PistonExtensionLifecycleBehaviour.BasePosition forFacingTwo = service.resolvePistonBaseForPhysics(10, 20, 30, 2);
        Assert.assertNotNull(forFacingTwo);
        Assert.assertEquals(10, forFacingTwo.getX());
        Assert.assertEquals(20, forFacingTwo.getY());
        Assert.assertEquals(31, forFacingTwo.getZ());

        PistonExtensionLifecycleBehaviour.BasePosition forFacingFive = service.resolvePistonBaseForPhysics(10, 20, 30, 5);
        Assert.assertNotNull(forFacingFive);
        Assert.assertEquals(9, forFacingFive.getX());
        Assert.assertEquals(20, forFacingFive.getY());
        Assert.assertEquals(30, forFacingFive.getZ());
    }

    @Test
    public void pistonBaseTypeCheckMatchesLegacyPistonIds() {
        PistonExtensionLifecycleBehaviour service = PistonExtensionLifecycleBehaviour.getInstance();

        Assert.assertTrue(service.isPistonBaseType(Block.PISTON.id));
        Assert.assertTrue(service.isPistonBaseType(Block.PISTON_STICKY.id));
        Assert.assertFalse(service.isPistonBaseType(Block.STONE.id));
    }
}
