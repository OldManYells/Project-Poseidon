package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftInventoryWrapperThinnessTest {
    private static final Path CRAFT_INVENTORY_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftInventory.java");

    @Test
    public void craftInventoryDelegatesItemConversionAndContentCopyPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_INVENTORY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("InventoryItemBridgeBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_ITEM_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("wrapSingle(getInventory().getItem(index))"));
        Assert.assertTrue(text.contains("toBukkitContents(mcItems, getSize())"));
        Assert.assertTrue(text.contains("copyContentsToNms(items, mcItems)"));
        Assert.assertTrue(text.contains("toNmsForSetItem(item)"));
        Assert.assertFalse(text.contains("items[i] = mcItems[i] == null ? null : new CraftItemStack(mcItems[i]);"));
        Assert.assertFalse(text.contains("if (item == null || item.getTypeId() <= 0)"));
        Assert.assertFalse(text.contains("new net.minecraft.server.ItemStack(item.getTypeId(), item.getAmount(), item.getDurability())"));
    }

    @Test
    public void craftInventoryDelegatesSearchAndLookupLoopsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_INVENTORY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("InventorySearchBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_SEARCH_BEHAVIOUR"));
        Assert.assertTrue(text.contains("containsMaterialId(getContents(), materialId)"));
        Assert.assertTrue(text.contains("allByMaterialId(getContents(), materialId)"));
        Assert.assertTrue(text.contains("firstPartialByItem(getContents(), item)"));
        Assert.assertFalse(text.contains("for (ItemStack item: getContents())"));
        Assert.assertFalse(text.contains("HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();"));
        Assert.assertFalse(text.contains("if (item != null && item.getTypeId() == materialId && item.getAmount() < item.getMaxStackSize())"));
    }

    @Test
    public void craftInventoryDelegatesRemoveAndClearLoopsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_INVENTORY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("InventoryRemovalBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_REMOVAL_BEHAVIOUR"));
        Assert.assertTrue(text.contains("removeMaterialId("));
        Assert.assertTrue(text.contains("removeMatchingItem("));
        Assert.assertTrue(text.contains("clearAll("));
        Assert.assertFalse(text.contains("if (items[i] != null && items[i].getTypeId() == materialId)"));
        Assert.assertFalse(text.contains("if (items[i] != null && items[i].equals(item))"));
        Assert.assertFalse(text.contains("for (int i = 0; i < getSize(); i++)"));
    }

    @Test
    public void craftInventoryDelegatesAddAndRemoveMutationLoopsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_INVENTORY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("InventoryTransactionBatchBehaviour"));
        Assert.assertTrue(text.contains("InventoryTransactionEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_TRANSACTION_BATCH_BEHAVIOUR"));
        Assert.assertTrue(text.contains("INVENTORY_TRANSACTION_EVENT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("processAdds("));
        Assert.assertTrue(text.contains("processRemovals("));
        Assert.assertTrue(text.contains("InventoryTransactionMutationBehaviour"));
        Assert.assertTrue(text.contains("INVENTORY_TRANSACTION_MUTATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("addItem(createMutationAccess(), item)"));
        Assert.assertTrue(text.contains("removeItem(createMutationAccess(), item)"));
        Assert.assertTrue(text.contains("createMutationAccess()"));
        Assert.assertFalse(text.contains("for (int i = 0; i < items.length; i++)"));
        Assert.assertFalse(text.contains("while (true) {"));
        Assert.assertFalse(text.contains("int firstPartial = firstPartial(item);"));
        Assert.assertFalse(text.contains("int toDelete = item.getAmount();"));
        Assert.assertFalse(text.contains("new InventoryTransactionEvent("));
        Assert.assertFalse(text.contains("Bukkit.getServer().getPluginManager().callEvent(event);"));
    }
}
