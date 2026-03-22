package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftHumanEntityWrapperThinnessTest {
    private static final Path CRAFT_HUMAN_ENTITY_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftHumanEntity.java");

    @Test
    public void craftHumanEntityDelegatesInventoryAndPermissionBridgeLogicToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_HUMAN_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("HumanInventoryBridgeBehaviour"));
        Assert.assertTrue(text.contains("HumanPermissionBridgeBehaviour"));
        Assert.assertTrue(text.contains("HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity)"));
        Assert.assertTrue(text.contains("HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.applyOperatorState(perm, value)"));
        Assert.assertTrue(text.contains("HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.getEffectivePermissions(perm)"));
        Assert.assertFalse(text.contains("new CraftInventoryPlayer(entity.inventory)"));
        Assert.assertFalse(text.contains("perm.recalculatePermissions();"));
        Assert.assertFalse(text.contains("return perm.getEffectivePermissions();"));
    }
}
