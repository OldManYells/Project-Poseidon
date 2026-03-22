package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftInventoryPlayerAndSlotWrapperThinnessTest {
    private static final Path CRAFT_INVENTORY_PLAYER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftInventoryPlayer.java");
    private static final Path CRAFT_SLOT_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftSlot.java");

    @Test
    public void craftInventoryPlayerDelegatesHandAndArmorBridgePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_INVENTORY_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PlayerInventoryBridgeBehaviour"));
        Assert.assertTrue(text.contains("PLAYER_INVENTORY_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("inventorySizeWithoutArmor(super.getSize())"));
        Assert.assertTrue(text.contains("toArmorContents(getInventory().getArmorContents())"));
        Assert.assertTrue(text.contains("applyArmorContents("));
        Assert.assertFalse(text.contains("return super.getSize() - 4;"));
        Assert.assertFalse(text.contains("CraftItemStack[] ret = new CraftItemStack[mcItems.length];"));
        Assert.assertFalse(text.contains("if (item == null || item.getTypeId() == 0)"));
    }

    @Test
    public void craftSlotDelegatesInventoryAndItemBridgePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SLOT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("InventorySlotBridgeBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_SLOT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getInventory(slot)"));
        Assert.assertTrue(text.contains("getItem(slot)"));
        Assert.assertFalse(text.contains("return new CraftInventory(slot.inventory);"));
        Assert.assertFalse(text.contains("return new CraftItemStack(slot.getItem());"));
    }
}
