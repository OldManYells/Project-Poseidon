package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTileEntityDispenserWrapperThinnessTest {
    private static final Path TILE_ENTITY_DISPENSER_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntityDispenser.java");

    @Test
    public void tileEntityDispenserDelegatesSlotSelectionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(TILE_ENTITY_DISPENSER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DispenserSlotSelectionBehaviour"));
        Assert.assertTrue(text.contains("DISPENSER_SLOT_SELECTION_BEHAVIOUR.findDispenseSlot(this.items, this.b)"));
        Assert.assertFalse(text.contains("int j = 1;"));
        Assert.assertFalse(text.contains("this.b.nextInt(j++)"));
    }
}
