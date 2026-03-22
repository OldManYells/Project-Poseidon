package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTerminalConsoleHandlerWrapperThinnessTest {
    private static final Path TERMINAL_CONSOLE_HANDLER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/util/TerminalConsoleHandler.java");

    @Test
    public void terminalConsoleHandlerDelegatesFlushFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(TERMINAL_CONSOLE_HANDLER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TerminalConsoleFlushBehaviour"));
        Assert.assertTrue(text.contains("terminalConsoleFlushBehaviour"));
        Assert.assertTrue(text.contains("terminalConsoleFlushBehaviour.flush(this.reader, Main.useJline"));
        Assert.assertTrue(text.contains("TerminalConsoleHandler.super.flush();"));
        Assert.assertFalse(text.contains("reader.drawLine();"));
        Assert.assertFalse(text.contains("reader.getCursorBuffer().clearBuffer();"));
        Assert.assertFalse(text.contains("reader.printString(ConsoleReader.RESET_LINE + \"\")"));
    }
}

