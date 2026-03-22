package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityFishWrapperThinnessTest {
    private static final Path ENTITY_FISH_PATH = Paths.get("src/main/java/net/minecraft/server/EntityFish.java");

    @Test
    public void entityFishDelegatesLaunchVectorComputationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_FISH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FishingHookLaunchBehaviour"));
        Assert.assertTrue(text.contains("FishingHookPersistenceBehaviour"));
        Assert.assertTrue(text.contains("FishingHookReelBehaviour"));
        Assert.assertTrue(text.contains("FISHING_HOOK_LAUNCH_BEHAVIOUR.createLaunchState"));
        Assert.assertTrue(text.contains("FISHING_HOOK_LAUNCH_BEHAVIOUR.resetTicksInGroundCounter()"));
        Assert.assertTrue(text.contains("FISHING_HOOK_PERSISTENCE_BEHAVIOUR.writeToNbt"));
        Assert.assertTrue(text.contains("FISHING_HOOK_PERSISTENCE_BEHAVIOUR.readFromNbt"));
        Assert.assertTrue(text.contains("FISHING_HOOK_REEL_BEHAVIOUR.computePullMotion"));
        Assert.assertFalse(text.contains("d0 += this.random.nextGaussian()"));
        Assert.assertFalse(text.contains("this.lastYaw = this.yaw = (float) (Math.atan2(d0, d2)"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"xTile\", (short) this.d);"));
        Assert.assertFalse(text.contains("this.d = nbttagcompound.d(\"xTile\");"));
        Assert.assertFalse(text.contains("double d0 = this.owner.locX - this.locX;"));
        Assert.assertFalse(text.contains("double d5 = this.owner.locX - this.locX;"));
    }
}
