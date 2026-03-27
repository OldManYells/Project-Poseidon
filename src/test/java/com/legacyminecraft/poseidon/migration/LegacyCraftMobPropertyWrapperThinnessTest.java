package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftMobPropertyWrapperThinnessTest {
    private static final Path CRAFT_SLIME_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSlime.java");
    private static final Path CRAFT_PIG_ZOMBIE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPigZombie.java");
    private static final Path CRAFT_PIG_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPig.java");
    private static final Path CRAFT_SHEEP_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSheep.java");

    @Test
    public void craftSlimeDelegatesSizePropertiesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SLIME_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MobPropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("MOB_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(super.getHandle(), EntitySlime.class)"));
        Assert.assertTrue(text.contains("getSlimeSize(getHandle())"));
        Assert.assertTrue(text.contains("setSlimeSize(getHandle(), size)"));
        Assert.assertFalse(text.contains("return (EntitySlime) super.getHandle();"));
        Assert.assertFalse(text.contains("getHandle().getSize()"));
        Assert.assertFalse(text.contains("getHandle().setSize(size)"));
    }

    @Test
    public void craftPigZombieDelegatesAngerPropertiesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_PIG_ZOMBIE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MobPropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("MOB_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(super.getHandle(), EntityPigZombie.class)"));
        Assert.assertTrue(text.contains("getPigZombieAnger(getHandle())"));
        Assert.assertTrue(text.contains("setPigZombieAnger(getHandle(), level)"));
        Assert.assertTrue(text.contains("setPigZombieAngry(getHandle(), angry)"));
        Assert.assertTrue(text.contains("isPigZombieAngry(getHandle())"));
        Assert.assertFalse(text.contains("return (EntityPigZombie) super.getHandle();"));
        Assert.assertFalse(text.contains("getHandle().angerLevel"));
        Assert.assertFalse(text.contains("setAnger(angry ? 400 : 0)"));
        Assert.assertFalse(text.contains("return getAnger() > 0;"));
    }

    @Test
    public void craftSheepDelegatesColorAndShearPropertiesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SHEEP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MobPropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("MOB_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(entity, EntitySheep.class)"));
        Assert.assertTrue(text.contains("getSheepColor(getHandle())"));
        Assert.assertTrue(text.contains("setSheepColor(getHandle(), color)"));
        Assert.assertTrue(text.contains("isSheepSheared(getHandle())"));
        Assert.assertTrue(text.contains("setSheepSheared(getHandle(), flag)"));
        Assert.assertFalse(text.contains("return (EntitySheep) entity;"));
        Assert.assertFalse(text.contains("DyeColor.getByData((byte) getHandle().getColor())"));
        Assert.assertFalse(text.contains("getHandle().setColor(color.getData())"));
        Assert.assertFalse(text.contains("getHandle().isSheared()"));
        Assert.assertFalse(text.contains("getHandle().setSheared(flag)"));
    }

    @Test
    public void craftPigDelegatesSaddlePropertiesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_PIG_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MobPropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("MOB_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(super.getHandle(), EntityPig.class)"));
        Assert.assertTrue(text.contains("hasPigSaddle(getHandle())"));
        Assert.assertTrue(text.contains("setPigSaddle(getHandle(), saddled)"));
        Assert.assertFalse(text.contains("return (EntityPig) super.getHandle();"));
        Assert.assertFalse(text.contains("getHandle().hasSaddle()"));
        Assert.assertFalse(text.contains("getHandle().setSaddle(saddled)"));
    }
}
