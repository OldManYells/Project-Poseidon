package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackerSystem;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPlayer;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackerServiceTest {
    private final EntityTrackerSystem service = EntityTrackerSystem.getInstance();

    @Test
    public void resolvesPlayerTrackingProfileAndCapsDistance() {
        EntityTrackerSystem.TrackingProfile profile = service.resolveTrackingProfile(EntityPlayer.class, 256);

        Assert.assertTrue(profile.isTracked());
        Assert.assertEquals(256, profile.getTrackingDistance());
        Assert.assertEquals(2, profile.getUpdateInterval());
        Assert.assertFalse(profile.isMoving());
        Assert.assertTrue(profile.shouldRefreshExistingTrackers());
    }

    @Test
    public void resolvesProjectileTrackingProfile() {
        EntityTrackerSystem.TrackingProfile profile = service.resolveTrackingProfile(EntityArrow.class, 512);

        Assert.assertTrue(profile.isTracked());
        Assert.assertEquals(64, profile.getTrackingDistance());
        Assert.assertEquals(20, profile.getUpdateInterval());
        Assert.assertFalse(profile.isMoving());
        Assert.assertFalse(profile.shouldRefreshExistingTrackers());
    }

    @Test
    public void resolvesAnimalFallbackTrackingProfile() {
        EntityTrackerSystem.TrackingProfile profile = service.resolveTrackingProfile(EntityPig.class, 128);

        Assert.assertTrue(profile.isTracked());
        Assert.assertEquals(128, profile.getTrackingDistance());
        Assert.assertEquals(3, profile.getUpdateInterval());
        Assert.assertFalse(profile.isMoving());
    }

    @Test
    public void resolvesPaintingProfileWithoutVelocity() {
        EntityTrackerSystem.TrackingProfile profile = service.resolveTrackingProfile(EntityPainting.class, 160);

        Assert.assertTrue(profile.isTracked());
        Assert.assertEquals(160, profile.getTrackingDistance());
        Assert.assertEquals(Integer.MAX_VALUE, profile.getUpdateInterval());
        Assert.assertFalse(profile.isMoving());
    }

    @Test
    public void leavesBaseEntityUntracked() {
        EntityTrackerSystem.TrackingProfile profile = service.resolveTrackingProfile(Entity.class, 160);

        Assert.assertFalse(profile.isTracked());
    }
}
