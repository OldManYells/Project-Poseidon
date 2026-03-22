package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftStorageMinecartWrapperThinnessTest {
    private static final Path CRAFT_STORAGE_MINECART_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftStorageMinecart.java");

    @Test
    public void craftStorageMinecartDelegatesInventoryBridgeToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_STORAGE_MINECART_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("StorageMinecartInventoryBridgeBehaviour"));
        Assert.assertTrue(text.contains("STORAGE_MINECART_INVENTORY_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createInventory(entity)"));
        Assert.assertTrue(text.contains("toInventory(inventory)"));
        Assert.assertFalse(text.contains("inventory = new CraftInventory(entity);"));
        Assert.assertFalse(text.contains("return inventory;"));
    }
}

