package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyServerThreadWrappersThinnessTest {
    private static final Path THREAD_COMMAND_READER_PATH = Paths.get("src/main/java/net/minecraft/server/ThreadCommandReader.java");
    private static final Path THREAD_SLEEP_FOREVER_PATH = Paths.get("src/main/java/net/minecraft/server/ThreadSleepForever.java");
    private static final Path THREAD_SERVER_APPLICATION_PATH = Paths.get("src/main/java/net/minecraft/server/ThreadServerApplication.java");

    @Test
    public void threadCommandReaderUsesCanonicalSystemNaming() throws IOException {
        String text = new String(Files.readAllBytes(THREAD_COMMAND_READER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConsoleInputLoopSystem"));
        Assert.assertTrue(text.contains("consoleInputLoopSystem"));
        Assert.assertTrue(text.contains("consoleLineReader"));
        Assert.assertTrue(text.contains("runningState"));
        Assert.assertTrue(text.contains("commandSink"));
        Assert.assertTrue(text.contains("LOGGER"));
        Assert.assertTrue(text.contains("ConsoleReaderExceptionPolicy"));
        Assert.assertTrue(text.contains("consoleReaderExceptionPolicy"));
        Assert.assertTrue(text.contains("consoleInputLoopSystem.runLoop("));
        Assert.assertFalse(text.contains("ConsoleInputLoopService"));
        Assert.assertFalse(text.contains("consoleInputLoopService"));
    }

    @Test
    public void threadSleepForeverUsesCanonicalSystemNaming() throws IOException {
        String text = new String(Files.readAllBytes(THREAD_SLEEP_FOREVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SleepForeverSystem"));
        Assert.assertTrue(text.contains("sleepForeverSystem"));
        Assert.assertTrue(text.contains("sleepForeverSystem.sleepForever();"));
        Assert.assertFalse(text.contains("sleepForeverService"));
    }

    @Test
    public void threadServerApplicationUsesCanonicalSystemNaming() throws IOException {
        String text = new String(Files.readAllBytes(THREAD_SERVER_APPLICATION_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ServerRunInvocationSystem"));
        Assert.assertTrue(text.contains("serverRunInvocationSystem"));
        Assert.assertTrue(text.contains("serverRunInvocationSystem.runServer(this.a);"));
        Assert.assertFalse(text.contains("serverRunInvocationService"));
    }
}
