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
        Assert.assertTrue(text.contains("HumanStateBridgeBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("EntityHandleMutationBehaviour"));
        Assert.assertTrue(text.contains("HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(entity)"));
        Assert.assertTrue(text.contains("HUMAN_INVENTORY_BRIDGE_BEHAVIOUR.toInventory(inventory)"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityHuman.class)"));
        Assert.assertTrue(text.contains("ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(entity, new EntityHandleMutationBehaviour.HandleMutationCallbacks()"));
        Assert.assertTrue(text.contains("HUMAN_STATE_BRIDGE_BEHAVIOUR.getName(getHandle())"));
        Assert.assertTrue(text.contains("HUMAN_STATE_BRIDGE_BEHAVIOUR.toString(getEntityId(), getName())"));
        Assert.assertTrue(text.contains("HUMAN_STATE_BRIDGE_BEHAVIOUR.isSleeping(getHandle())"));
        Assert.assertTrue(text.contains("HUMAN_STATE_BRIDGE_BEHAVIOUR.getSleepTicks(getHandle())"));
        Assert.assertTrue(text.contains("HUMAN_STATE_BRIDGE_BEHAVIOUR.isOperator(op)"));
        Assert.assertTrue(text.contains("HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.applyOperatorState(perm, value)"));
        Assert.assertTrue(text.contains("HUMAN_PERMISSION_BRIDGE_BEHAVIOUR.getEffectivePermissions(perm)"));
        Assert.assertFalse(text.contains("new CraftInventoryPlayer(entity.inventory)"));
        Assert.assertFalse(text.contains("return inventory;"));
        Assert.assertFalse(text.contains("return (EntityHuman) entity;"));
        Assert.assertFalse(text.contains("super.setHandle((EntityHuman) entity);"));
        Assert.assertFalse(text.contains("return getHandle().name;"));
        Assert.assertFalse(text.contains("return getHandle().sleeping;"));
        Assert.assertFalse(text.contains("return getHandle().sleepTicks;"));
        Assert.assertFalse(text.contains("perm.recalculatePermissions();"));
        Assert.assertFalse(text.contains("return perm.getEffectivePermissions();"));
    }
}
