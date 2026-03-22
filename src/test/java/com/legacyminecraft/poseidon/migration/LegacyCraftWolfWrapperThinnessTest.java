package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWolfWrapperThinnessTest {
    private static final Path CRAFT_WOLF_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftWolf.java");

    @Test
    public void craftWolfDelegatesStateAndOwnerPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_WOLF_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WolfStateBehaviour"));
        Assert.assertTrue(text.contains("WOLF_STATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveOwner(owner, getServer(), getOwnerName())"));
        Assert.assertTrue(text.contains("applyOwner(getHandle(), tamer)"));
        Assert.assertFalse(text.contains("owner = getServer().getPlayer(getOwnerName());"));
        Assert.assertFalse(text.contains("if (owner instanceof Player)"));
        Assert.assertFalse(text.contains("getHandle().setPathEntity(pathentity);"));
    }
}
