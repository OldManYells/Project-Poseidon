package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityListWrapperThinnessTest {
    private static final Path ENTITY_LIST_PATH = Paths.get("src/main/java/net/minecraft/server/EntityList.java");

    @Test
    public void entityListDelegatesIntMapOperationsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_LIST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityIntMapBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_INT_MAP_BEHAVIOUR.get(this, i)"));
        Assert.assertTrue(text.contains("ENTITY_INT_MAP_BEHAVIOUR.put(this, i, object)"));
        Assert.assertTrue(text.contains("ENTITY_INT_MAP_BEHAVIOUR.remove(this, i)"));
        Assert.assertTrue(text.contains("ENTITY_INT_MAP_BEHAVIOUR.clear(this)"));
        Assert.assertTrue(text.contains("ENTITY_INT_MAP_BEHAVIOUR.findEntry(this, i)"));
        Assert.assertFalse(text.contains("this.a[k] = new EntityListEntry"));
        Assert.assertFalse(text.contains("if (this.b++ >= this.c)"));
        Assert.assertFalse(text.contains("for (EntityListEntry entitylistentry = this.a[a(j, this.a.length)]"));
    }
}
