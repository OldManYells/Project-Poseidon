package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftCreeperWrapperThinnessTest {
    private static final Path CRAFT_CREEPER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftCreeper.java");

    @Test
    public void craftCreeperDelegatesPowerEventOrchestrationToCanonicalBridge() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CREEPER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CreeperPowerEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(super.getHandle(), EntityCreeper.class)"));
        Assert.assertTrue(text.contains("CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR.isPowered(getHandle())"));
        Assert.assertTrue(text.contains("setPoweredWithEvent(this.server, getHandle(), powered)"));
        Assert.assertFalse(text.contains("return (EntityCreeper) super.getHandle();"));
        Assert.assertFalse(text.contains("return getHandle().isPowered();"));
        Assert.assertFalse(text.contains("new CreeperPowerEvent("));
        Assert.assertFalse(text.contains("server.getPluginManager().callEvent(event);"));
        Assert.assertFalse(text.contains("getHandle().setPowered(true);"));
        Assert.assertFalse(text.contains("getHandle().setPowered(false);"));
    }
}
