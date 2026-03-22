package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftItemStackWrapperThinnessTest {
    private static final Path CRAFT_ITEM_STACK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftItemStack.java");

    @Test
    public void craftItemStackDelegatesStateSyncAndMutationPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ITEM_STACK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftItemStackStateBridgeBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("applyTypeId(item, type, createSuperItemAccess())"));
        Assert.assertTrue(text.contains("applyAmount(item, amount, createSuperItemAccess())"));
        Assert.assertTrue(text.contains("syncDurability(item, createSuperItemAccess())"));
        Assert.assertFalse(text.contains("if (type == 0)"));
        Assert.assertFalse(text.contains("if (amount == 0)"));
        Assert.assertFalse(text.contains("if (item != null)"));
        Assert.assertFalse(text.contains("item.damage = durability;"));
    }
}
