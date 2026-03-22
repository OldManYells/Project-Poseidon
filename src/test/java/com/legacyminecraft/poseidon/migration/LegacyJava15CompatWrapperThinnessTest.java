package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyJava15CompatWrapperThinnessTest {
    private static final Path JAVA_15_COMPAT_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/util/Java15Compat.java");

    @Test
    public void java15CompatDelegatesArrayCopyLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(JAVA_15_COMPAT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("JavaArrayCopyBehaviour"));
        Assert.assertTrue(text.contains("JAVA_ARRAY_COPY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("JAVA_ARRAY_COPY_BEHAVIOUR.copyOf(original, newLength);"));
        Assert.assertFalse(text.contains("private static long[] Arrays_copyOfRange"));
        Assert.assertFalse(text.contains("Array.newInstance("));
    }
}

