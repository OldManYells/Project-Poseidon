package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyLoggerOutputStreamWrapperThinnessTest {
    private static final Path LOGGER_OUTPUT_STREAM_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/LoggerOutputStream.java");

    @Test
    public void loggerOutputStreamDelegatesFlushPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(LOGGER_OUTPUT_STREAM_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LoggerOutputFlushBehaviour"));
        Assert.assertTrue(text.contains("loggerOutputFlushBehaviour"));
        Assert.assertTrue(text.contains("loggerOutputFlushBehaviour.flush(this, this.separator, this.logger, this.level);"));
        Assert.assertFalse(text.contains("synchronized (this)"));
        Assert.assertFalse(text.contains("logger.logp(level, \"\", \"\", record);"));
    }
}

