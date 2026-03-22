package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTileEntityFurnaceWrapperThinnessTest {
    private static final Path TILE_ENTITY_FURNACE_PATH =
            Paths.get("src/main/java/net/minecraft/server/TileEntityFurnace.java");

    @Test
    public void tileEntityFurnaceDelegatesBukkitBurnAndSmeltEventsToCanonicalBridge() throws IOException {
        String text = new String(Files.readAllBytes(TILE_ENTITY_FURNACE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FurnaceEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("FURNACE_EVENT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("FURNACE_EVENT_BRIDGE_BEHAVIOUR.fireBurnEvent("));
        Assert.assertTrue(text.contains("FURNACE_EVENT_BRIDGE_BEHAVIOUR.fireSmeltEvent("));
        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.inventory.CraftItemStack;"));
        Assert.assertFalse(text.contains("import org.bukkit.event.inventory.FurnaceBurnEvent;"));
        Assert.assertFalse(text.contains("import org.bukkit.event.inventory.FurnaceSmeltEvent;"));
        Assert.assertFalse(text.contains("new FurnaceBurnEvent("));
        Assert.assertFalse(text.contains("new FurnaceSmeltEvent("));
        Assert.assertFalse(text.contains("new CraftItemStack("));
    }
}

