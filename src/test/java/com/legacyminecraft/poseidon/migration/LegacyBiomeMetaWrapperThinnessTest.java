package com.legacyminecraft.poseidon.migration;

import net.minecraft.server.BiomeMeta;
import net.minecraft.server.EntityZombie;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyBiomeMetaWrapperThinnessTest {
    private static final Path BIOME_META_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeMeta.java");

    @Test
    public void biomeMetaDelegatesInitializationAndPreservesLegacyFields() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_META_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeSpawnEntryBehaviour"));
        Assert.assertTrue(text.contains("BIOME_SPAWN_ENTRY_BEHAVIOUR.initializeLegacyEntry"));
        Assert.assertTrue(text.contains("public Class a;"));
        Assert.assertTrue(text.contains("public int b;"));
        Assert.assertFalse(text.contains("this.a = oclass;"));
        Assert.assertFalse(text.contains("this.b = i;"));

        BiomeMeta entry = new BiomeMeta(EntityZombie.class, 10);
        Assert.assertEquals(EntityZombie.class, entry.a);
        Assert.assertEquals(10, entry.b);
        Assert.assertEquals(EntityZombie.class, entry.getEntityClass());
        Assert.assertEquals(10, entry.getSpawnWeight());
    }
}
