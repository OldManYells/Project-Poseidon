package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTileEntityBlockWrapperThinnessTest {
    private static final Path CRAFT_CHEST_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftChest.java");
    private static final Path CRAFT_DISPENSER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftDispenser.java");
    private static final Path CRAFT_FURNACE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftFurnace.java");
    private static final Path CRAFT_CREATURE_SPAWNER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftCreatureSpawner.java");
    private static final Path CRAFT_NOTE_BLOCK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftNoteBlock.java");
    private static final Path CRAFT_SIGN_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftSign.java");

    @Test
    public void tileEntityBackedCraftBlockWrappersDelegateTileEntityLookupToCanonicalBehaviour() throws IOException {
        assertTileLookupDelegation(CRAFT_CHEST_PATH, "resolveChest(block, getX(), getY(), getZ())");
        assertTileLookupDelegation(CRAFT_DISPENSER_PATH, "resolveDispenser(block, getX(), getY(), getZ())");
        assertTileLookupDelegation(CRAFT_FURNACE_PATH, "resolveFurnace(block, getX(), getY(), getZ())");
        assertTileLookupDelegation(CRAFT_CREATURE_SPAWNER_PATH, "resolveMobSpawner(block, getX(), getY(), getZ())");
        assertTileLookupDelegation(CRAFT_NOTE_BLOCK_PATH, "resolveNote(block, getX(), getY(), getZ())");
        assertTileLookupDelegation(CRAFT_SIGN_PATH, "resolveSign(block, getX(), getY(), getZ())");
    }

    private void assertTileLookupDelegation(Path wrapperPath, String expectedLookupCall) throws IOException {
        String text = new String(Files.readAllBytes(wrapperPath), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TileEntityLookupBehaviour"));
        Assert.assertTrue(text.contains("TILE_ENTITY_LOOKUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains(expectedLookupCall));
        Assert.assertFalse(text.contains("(CraftWorld) block.getWorld()"));
        Assert.assertFalse(text.contains(".getTileEntityAt(getX(), getY(), getZ())"));
    }
}
