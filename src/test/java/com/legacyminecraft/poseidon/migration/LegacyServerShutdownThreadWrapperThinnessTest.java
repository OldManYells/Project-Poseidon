package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyServerShutdownThreadWrapperThinnessTest {
    private static final Path SERVER_SHUTDOWN_THREAD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/util/ServerShutdownThread.java");

    @Test
    public void serverShutdownThreadDelegatesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(SERVER_SHUTDOWN_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ServerShutdownThreadBehaviour"));
        Assert.assertTrue(text.contains("serverShutdownThreadBehaviour"));
        Assert.assertTrue(text.contains("serverShutdownThreadBehaviour.requestStop(this.server);"));
        Assert.assertFalse(text.contains("server.stop();"));
    }
}

