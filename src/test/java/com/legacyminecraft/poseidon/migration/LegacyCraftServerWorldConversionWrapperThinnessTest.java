package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldConversionWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_CONVERSION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldConversionBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldConversionWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_CONVERSION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldConversionBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_CONVERSION_BEHAVIOUR.convertIfNeeded(folder, worldName, console, getLogger());"));

        Assert.assertFalse(section.contains("Convertable converter = new WorldLoaderServer(folder);"));
        Assert.assertFalse(section.contains("if (converter.isConvertable(name))"));
        Assert.assertFalse(section.contains("getLogger().info(\"Converting world '\" + name + \"'\")"));
        Assert.assertFalse(section.contains("converter.convert(name, new ConvertProgressUpdater(console));"));

        Assert.assertTrue(behaviourText.contains("convertIfNeeded(File folder, String name, MinecraftServer console, Logger logger)"));
        Assert.assertTrue(behaviourText.contains("Convertable converter = new WorldLoaderServer(folder);"));
        Assert.assertTrue(behaviourText.contains("if (converter.isConvertable(name)) {"));
        Assert.assertTrue(behaviourText.contains("logger.info(\"Converting world '\" + name + \"'\");"));
        Assert.assertTrue(behaviourText.contains("converter.convert(name, new ConvertProgressUpdater(console));"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
