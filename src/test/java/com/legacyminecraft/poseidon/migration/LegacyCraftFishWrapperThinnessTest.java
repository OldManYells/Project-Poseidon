package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftFishWrapperThinnessTest {
    private static final Path CRAFT_FISH_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFish.java");

    @Test
    public void craftFishDelegatesOwnerConversionToCanonicalBridgeBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FISH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FishHookOwnerBridgeBehaviour"));
        Assert.assertTrue(text.contains("FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toBukkitOwner(((EntityFish) getHandle()).owner)"));
        Assert.assertTrue(text.contains("toNmsOwner(shooter)"));
        Assert.assertFalse(text.contains("if (((EntityFish) getHandle()).owner != null)"));
        Assert.assertFalse(text.contains("owner.getBukkitEntity()"));
        Assert.assertFalse(text.contains("shooter instanceof CraftHumanEntity"));
        Assert.assertFalse(text.contains("(EntityHuman) ((CraftHumanEntity) shooter).entity"));
    }
}

