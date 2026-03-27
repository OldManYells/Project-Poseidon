package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldDuplicateWarningWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_DUPLICATE_WARNING_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldDuplicateWarningBehaviour.java");

    @Test
    public void craftServerDelegatesDuplicateWorldWarningWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_DUPLICATE_WARNING_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void addWorld(World world) {", "public Logger getLogger() {");

        Assert.assertTrue(craftServerText.contains("CraftServerWorldDuplicateWarningBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_DUPLICATE_WARNING_BEHAVIOUR.warnDuplicateWorld(duplicateWorld);"));
        Assert.assertFalse(section.contains("System.out.println(\"World \" + world.getName() + \" is a duplicate"));

        Assert.assertTrue(behaviourText.contains("warnDuplicateWorld(World world)"));
        Assert.assertTrue(behaviourText.contains("System.out.println("));
        Assert.assertTrue(behaviourText.contains("is a duplicate of another world and has been prevented from loading"));
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
