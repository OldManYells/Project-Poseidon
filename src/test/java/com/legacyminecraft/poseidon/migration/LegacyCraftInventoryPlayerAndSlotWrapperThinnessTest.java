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
    private static final Path PLAYER_INVENTORY_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/PlayerInventoryBridgeBehaviour.java");
    private static final Path INVENTORY_SLOT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/InventorySlotBridgeBehaviour.java");

    @Test
    public void craftInventoryPlayerDelegatesHandAndArmorBridgePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_INVENTORY_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PlayerInventoryBridgeBehaviour"));
        Assert.assertTrue(text.contains("PLAYER_INVENTORY_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getInventory(inventory)"));
        Assert.assertTrue(text.contains("inventorySizeWithoutArmor(super.getSize())"));
        Assert.assertTrue(text.contains("getItemInHand(getInventory())"));
        Assert.assertTrue(text.contains("setItemInHand(this, stack)"));
        Assert.assertTrue(text.contains("getHelmet(this)"));
        Assert.assertTrue(text.contains("setBoots(this, boots)"));
        Assert.assertTrue(text.contains("toArmorContents(getInventory().getArmorContents())"));
        Assert.assertTrue(text.contains("applyArmorContents("));
        Assert.assertFalse(text.contains("return (InventoryPlayer) inventory;"));
        Assert.assertFalse(text.contains("setItem(getHeldItemSlot(), stack);"));
        Assert.assertFalse(text.contains("getItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 3))"));
        Assert.assertFalse(text.contains("setItem(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR.armorSlotIndex(getSize(), 0), boots);"));
    }

    @Test
    public void craftSlotDelegatesInventoryAndItemBridgePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SLOT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("InventorySlotBridgeBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_SLOT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getInventory(slot)"));
        Assert.assertTrue(text.contains("getIndex(slot)"));
        Assert.assertTrue(text.contains("getItem(slot)"));
        Assert.assertFalse(text.contains("return slot.index;"));
    }

    @Test
    public void playerInventoryBridgeBehaviourOwnsInventoryAndEquipmentAccessors() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_INVENTORY_BRIDGE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("getInventory(net.minecraft.server.IInventory inventory)"));
        Assert.assertTrue(text.contains("return (InventoryPlayer) inventory;"));
        Assert.assertTrue(text.contains("setItemInHand(CraftInventoryPlayer inventoryPlayer, ItemStack stack)"));
        Assert.assertTrue(text.contains("getHelmet(CraftInventoryPlayer inventoryPlayer)"));
        Assert.assertTrue(text.contains("setBoots(CraftInventoryPlayer inventoryPlayer, ItemStack boots)"));
    }

    @Test
    public void inventorySlotBridgeBehaviourOwnsSlotIndexLookup() throws IOException {
        String text = new String(Files.readAllBytes(INVENTORY_SLOT_BRIDGE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("getIndex(Slot slot)"));
        Assert.assertTrue(text.contains("return slot.index;"));
    }
}
