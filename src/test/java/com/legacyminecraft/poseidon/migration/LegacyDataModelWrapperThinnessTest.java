package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyDataModelWrapperThinnessTest {
    private static final Path NIBBLE_ARRAY_PATH = Paths.get("src/main/java/net/minecraft/server/NibbleArray.java");
    private static final Path WATCHABLE_OBJECT_PATH = Paths.get("src/main/java/net/minecraft/server/WatchableObject.java");

    @Test
    public void nibbleArrayDelegatesIndexingAndBitPackingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NIBBLE_ARRAY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NibbleArrayBehaviour"));
        Assert.assertTrue(text.contains("NIBBLE_ARRAY_BEHAVIOUR.createBackingArray"));
        Assert.assertTrue(text.contains("NIBBLE_ARRAY_BEHAVIOUR.getValue"));
        Assert.assertTrue(text.contains("NIBBLE_ARRAY_BEHAVIOUR.setValue"));
        Assert.assertFalse(text.contains("int l = i << 11 | k << 7 | j;"));
        Assert.assertFalse(text.contains("this.a[j1] = (byte) (this.a[j1] & 240 | l & 15)"));
    }

    @Test
    public void watchableObjectDelegatesStateOperationsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WATCHABLE_OBJECT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WatchableObjectStateBehaviour"));
        Assert.assertTrue(text.contains("WATCHABLE_OBJECT_STATE_BEHAVIOUR.initialize"));
        Assert.assertTrue(text.contains("WATCHABLE_OBJECT_STATE_BEHAVIOUR.getObjectId"));
        Assert.assertTrue(text.contains("WATCHABLE_OBJECT_STATE_BEHAVIOUR.setValue"));
        Assert.assertTrue(text.contains("WATCHABLE_OBJECT_STATE_BEHAVIOUR.setDirty"));
        Assert.assertFalse(text.contains("this.d = true;"));
        Assert.assertFalse(text.contains("this.c = object;"));
    }
}
