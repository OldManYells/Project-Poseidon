package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyDataWatcherWrapperThinnessTest {
    private static final Path DATA_WATCHER_PATH = Paths.get("src/main/java/net/minecraft/server/DataWatcher.java");

    @Test
    public void dataWatcherDelegatesRegistrationDirtyTrackingAndCodecToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(DATA_WATCHER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DataWatcherCodecBehaviour"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.createSupportedTypeMap"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.register"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.watch"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.collectDirty"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.writeList"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.writeAll"));
        Assert.assertTrue(text.contains("DATA_WATCHER_CODEC_BEHAVIOUR.readList"));
        Assert.assertFalse(text.contains("a.put(Byte.class, Integer.valueOf(0))"));
        Assert.assertFalse(text.contains("dataoutputstream.writeByte(127)"));
        Assert.assertFalse(text.contains("for (byte b0 = datainputstream.readByte(); b0 != 127;"));
    }
}
