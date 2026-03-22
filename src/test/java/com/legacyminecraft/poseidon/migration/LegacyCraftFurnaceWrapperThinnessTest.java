package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftFurnaceWrapperThinnessTest {
    private static final Path CRAFT_FURNACE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftFurnace.java");

    @Test
    public void craftFurnaceDelegatesInventoryAndBurnCookStateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FURNACE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FurnaceBlockStateBehaviour"));
        Assert.assertTrue(text.contains("FURNACE_BLOCK_STATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createInventory(furnace)"));
        Assert.assertTrue(text.contains("finalizeUpdate(super.update(force), furnace)"));
        Assert.assertTrue(text.contains("setBurnTime(furnace, burnTime)"));
        Assert.assertTrue(text.contains("setCookTime(furnace, cookTime)"));
        Assert.assertFalse(text.contains("return new CraftInventory(furnace);"));
        Assert.assertFalse(text.contains("if (result) {"));
        Assert.assertFalse(text.contains("furnace.burnTime = burnTime;"));
    }
}
