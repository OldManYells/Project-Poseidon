package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerMetadataWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_METADATA_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerMetadataBehaviour.java");

    @Test
    public void craftServerDelegatesMetadataStringWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_METADATA_BEHAVIOUR_PATH);
        String toStringSection = section(craftServerText, "@Override\n    public String toString() {", "public World createWorld(String name, World.Environment environment) {");

        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.toString(serverName, serverVersion, protocolVersion)"));
        Assert.assertFalse(toStringSection.contains("\"CraftServer{\" + \"serverName=\" + serverName + \",serverVersion=\" + serverVersion + \",protocolVersion=\" + protocolVersion + '}'"));

        Assert.assertTrue(behaviourText.contains("toString(String serverName, String serverVersion, String protocolVersion)"));
        Assert.assertTrue(behaviourText.contains("return \"CraftServer{\" + \"serverName=\" + serverName + \",serverVersion=\" + serverVersion + \",protocolVersion=\" + protocolVersion + '}';"));
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
