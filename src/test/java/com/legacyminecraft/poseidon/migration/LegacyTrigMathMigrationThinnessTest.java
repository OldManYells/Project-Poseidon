package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTrigMathMigrationThinnessTest {
    private static final Path TRIG_MATH_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/TrigMath.java");
    private static final Path ENTITY_CREATURE_PATH = Paths.get("src/main/java/net/minecraft/server/EntityCreature.java");
    private static final Path ENTITY_LIVING_PATH = Paths.get("src/main/java/net/minecraft/server/EntityLiving.java");
    private static final Path ENTITY_HUMAN_PATH = Paths.get("src/main/java/net/minecraft/server/EntityHuman.java");

    @Test
    public void trigMathIsCanonicalAndNmsCallSitesNoLongerDependOnCraftBukkitTrigClass() throws IOException {
        String trigMathText = read(TRIG_MATH_PATH);
        String entityCreatureText = read(ENTITY_CREATURE_PATH);
        String entityLivingText = read(ENTITY_LIVING_PATH);
        String entityHumanText = read(ENTITY_HUMAN_PATH);

        Assert.assertTrue(trigMathText.contains("TrigAtanBehaviour"));
        Assert.assertTrue(trigMathText.contains("TRIG_ATAN_BEHAVIOUR"));
        Assert.assertTrue(trigMathText.contains("return TRIG_ATAN_BEHAVIOUR.atan(arg);"));
        Assert.assertTrue(trigMathText.contains("return TRIG_ATAN_BEHAVIOUR.atan2(arg1, arg2);"));
        Assert.assertFalse(trigMathText.contains("static final double sq2p1"));
        Assert.assertFalse(trigMathText.contains("private static double mxatan"));

        Assert.assertFalse(entityCreatureText.contains("import org.bukkit.craftbukkit.TrigMath;"));
        Assert.assertTrue(entityCreatureText.contains("TRIG_ATAN_BEHAVIOUR.atan2"));
        Assert.assertFalse(entityLivingText.contains("import org.bukkit.craftbukkit.TrigMath;"));
        Assert.assertTrue(entityLivingText.contains("TRIG_ATAN_BEHAVIOUR.atan2"));
        Assert.assertFalse(entityHumanText.contains("import org.bukkit.craftbukkit.TrigMath;"));
        Assert.assertTrue(entityHumanText.contains("TRIG_ATAN_BEHAVIOUR.atan("));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

