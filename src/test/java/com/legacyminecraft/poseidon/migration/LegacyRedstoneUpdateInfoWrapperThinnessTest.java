package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyRedstoneUpdateInfoWrapperThinnessTest {
    private static final Path REDSTONE_UPDATE_INFO_PATH = Paths.get("src/main/java/net/minecraft/server/RedstoneUpdateInfo.java");

    @Test
    public void redstoneUpdateInfoDelegatesInitializationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(REDSTONE_UPDATE_INFO_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RedstoneUpdateStateBehaviour"));
        Assert.assertTrue(text.contains("REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveX(i)"));
        Assert.assertTrue(text.contains("REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveY(j)"));
        Assert.assertTrue(text.contains("REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveZ(k)"));
        Assert.assertTrue(text.contains("REDSTONE_UPDATE_STATE_BEHAVIOUR.resolveScheduledTick(l)"));
        Assert.assertFalse(text.contains("this.a = i;"));
        Assert.assertFalse(text.contains("this.b = j;"));
        Assert.assertFalse(text.contains("this.c = k;"));
        Assert.assertFalse(text.contains("this.d = l;"));
    }
}
