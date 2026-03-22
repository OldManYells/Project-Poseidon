package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldSchedulingWrapperThinnessTest {
    private static final Path NEXT_TICK_LIST_ENTRY_PATH = Paths.get("src/main/java/net/minecraft/server/NextTickListEntry.java");

    @Test
    public void nextTickListEntryDelegatesOrderingAndHashPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NEXT_TICK_LIST_ENTRY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NextTickEntryOrderingBehaviour"));
        Assert.assertTrue(text.contains("NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.nextSequence"));
        Assert.assertTrue(text.contains("NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.incrementCounter"));
        Assert.assertTrue(text.contains("NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.matches"));
        Assert.assertTrue(text.contains("NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.hash"));
        Assert.assertTrue(text.contains("NEXT_TICK_ENTRY_ORDERING_BEHAVIOUR.compare"));
        Assert.assertFalse(text.contains("this.g = (long) (f++)"));
        Assert.assertFalse(text.contains("(this.a * 128 * 1024 + this.c * 128 + this.b) * 256 + this.d"));
        Assert.assertFalse(text.contains("this.e < nextticklistentry.e ? -1"));
    }
}
