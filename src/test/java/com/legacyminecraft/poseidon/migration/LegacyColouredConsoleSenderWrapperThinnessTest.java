package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyColouredConsoleSenderWrapperThinnessTest {
    private static final Path COLOURED_CONSOLE_SENDER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/command/ColouredConsoleSender.java");

    @Test
    public void colouredConsoleSenderDelegatesAnsiMappingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(COLOURED_CONSOLE_SENDER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConsoleColorRenderBehaviour"));
        Assert.assertTrue(text.contains("consoleColorRenderBehaviour"));
        Assert.assertTrue(text.contains("initializeDefaultReplacements(this.replacements)"));
        Assert.assertTrue(text.contains("renderAnsiMessage(message, this.colors, this.replacements)"));
        Assert.assertTrue(text.contains("consoleColorRenderBehaviour.resetAnsiCode()"));
        Assert.assertFalse(text.contains("replacements.put(ChatColor.BLACK"));
        Assert.assertFalse(text.contains("for (ChatColor color : colors)"));
    }
}

