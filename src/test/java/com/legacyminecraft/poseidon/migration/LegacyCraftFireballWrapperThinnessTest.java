package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftFireballWrapperThinnessTest {
    private static final Path CRAFT_FIREBALL_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFireball.java");

    @Test
    public void craftFireballDelegatesPropertyAndDirectionLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FIREBALL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FireballEntityPropertyBehaviour"));
        Assert.assertTrue(text.contains("FIREBALL_ENTITY_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getYield((EntityFireball) getHandle())"));
        Assert.assertTrue(text.contains("isIncendiary((EntityFireball) getHandle())"));
        Assert.assertTrue(text.contains("setIncendiary((EntityFireball) getHandle(), isIncendiary)"));
        Assert.assertTrue(text.contains("setYield((EntityFireball) getHandle(), yield)"));
        Assert.assertTrue(text.contains("getDirection((EntityFireball) getHandle())"));
        Assert.assertTrue(text.contains("setDirection((EntityFireball) getHandle(), direction)"));
        Assert.assertFalse(text.contains("((EntityFireball) getHandle()).yield"));
        Assert.assertFalse(text.contains("((EntityFireball) getHandle()).isIncendiary"));
        Assert.assertFalse(text.contains("new Vector(((EntityFireball) getHandle()).c"));
        Assert.assertFalse(text.contains("((EntityFireball) getHandle()).setDirection(direction.getX(), direction.getY(), direction.getZ())"));
    }
}

