package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftCreatureWrapperThinnessTest {
    private static final Path CRAFT_CREATURE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftCreature.java");

    @Test
    public void craftCreatureDelegatesTargetBridgeLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CREATURE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CreatureTargetBridgeBehaviour"));
        Assert.assertTrue(text.contains("CREATURE_TARGET_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CREATURE_TARGET_BRIDGE_BEHAVIOUR.setTarget(getHandle(), target)"));
        Assert.assertTrue(text.contains("CREATURE_TARGET_BRIDGE_BEHAVIOUR.getTarget(getHandle())"));
        Assert.assertFalse(text.contains("entity.world.findPath(entity, entity.target, 16.0F)"));
        Assert.assertFalse(text.contains("target instanceof CraftLivingEntity"));
        Assert.assertFalse(text.contains("getHandle().target == null"));
    }
}
