package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyShortConsoleLogFormatterWrapperThinnessTest {
    private static final Path SHORT_CONSOLE_LOG_FORMATTER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/util/ShortConsoleLogFormatter.java");

    @Test
    public void shortConsoleLogFormatterDelegatesFormattingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(SHORT_CONSOLE_LOG_FORMATTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ShortConsoleLogFormatBehaviour"));
        Assert.assertTrue(text.contains("shortConsoleLogFormatBehaviour"));
        Assert.assertTrue(text.contains("resolveDateFormat(server.options)"));
        Assert.assertTrue(text.contains("formatRecord(record, this.date)"));
        Assert.assertFalse(text.contains("options.has(\"date-format\")"));
        Assert.assertFalse(text.contains("new StringBuilder()"));
        Assert.assertFalse(text.contains("new StringWriter()"));
    }
}

