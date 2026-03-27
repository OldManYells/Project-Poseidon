package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftExplosiveAndItemWrapperThinnessTest {
    private static final Path CRAFT_TNT_PRIMED_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftTNTPrimed.java");
    private static final Path CRAFT_ITEM_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftItem.java");
    private static final Path CRAFT_LIGHTNING_STRIKE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftLightningStrike.java");

    @Test
    public void craftTntPrimedDelegatesPropertiesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_TNT_PRIMED_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PrimedTntPropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("PRIMED_TNT_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(super.getHandle(), EntityTNTPrimed.class)"));
        Assert.assertTrue(text.contains("getYield(getHandle())"));
        Assert.assertTrue(text.contains("isIncendiary(getHandle())"));
        Assert.assertTrue(text.contains("setIncendiary(getHandle(), isIncendiary)"));
        Assert.assertTrue(text.contains("setYield(getHandle(), yield)"));
        Assert.assertTrue(text.contains("getFuseTicks(getHandle())"));
        Assert.assertTrue(text.contains("setFuseTicks(getHandle(), fuseTicks)"));
        Assert.assertFalse(text.contains("getHandle().yield"));
        Assert.assertFalse(text.contains("getHandle().isIncendiary"));
        Assert.assertFalse(text.contains("getHandle().fuseTicks"));
        Assert.assertFalse(text.contains("return (EntityTNTPrimed) super.getHandle();"));
    }

    @Test
    public void craftItemDelegatesStackBridgingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ITEM_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ItemEntityStackBridgeBehaviour"));
        Assert.assertTrue(text.contains("ITEM_ENTITY_STACK_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toBukkitItemStack(item)"));
        Assert.assertTrue(text.contains("applyBukkitItemStack(item, stack)"));
        Assert.assertFalse(text.contains("new CraftItemStack(item.itemStack)"));
        Assert.assertFalse(text.contains("item.itemStack = new net.minecraft.server.ItemStack"));
    }

    @Test
    public void craftLightningStrikeDelegatesEffectPropertyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIGHTNING_STRIKE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LightningStrikePropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("LIGHTNING_STRIKE_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(super.getHandle(), EntityWeatherStorm.class)"));
        Assert.assertTrue(text.contains("isEffect(getHandle())"));
        Assert.assertFalse(text.contains("return (EntityWeatherStorm) super.getHandle();"));
        Assert.assertFalse(text.contains("((EntityWeatherStorm) super.getHandle()).isEffect"));
    }
}
