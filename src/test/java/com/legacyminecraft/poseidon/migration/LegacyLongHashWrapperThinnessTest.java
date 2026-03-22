package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyLongHashWrapperThinnessTest {
    private static final Path LONG_HASH_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/util/LongHash.java");

    @Test
    public void longHashDelegatesKeyPackingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(LONG_HASH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LongHashKeyBehaviour"));
        Assert.assertTrue(text.contains("LONG_HASH_KEY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("LONG_HASH_KEY_BEHAVIOUR.toLong(msw, lsw)"));
        Assert.assertTrue(text.contains("LONG_HASH_KEY_BEHAVIOUR.mostSignificantWord(l)"));
        Assert.assertTrue(text.contains("LONG_HASH_KEY_BEHAVIOUR.leastSignificantWord(l)"));
        Assert.assertFalse(text.contains("((long) msw << 32) + lsw - Integer.MIN_VALUE"));
    }
}

