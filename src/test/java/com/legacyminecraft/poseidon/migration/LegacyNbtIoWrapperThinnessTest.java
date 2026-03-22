package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNbtIoWrapperThinnessTest {
    private static final Path COMPRESSED_STREAM_TOOLS_PATH = Paths.get("src/main/java/net/minecraft/server/CompressedStreamTools.java");

    @Test
    public void compressedStreamToolsDelegatesCompressionAndRootValidationToCanonicalCodec() throws IOException {
        String text = new String(Files.readAllBytes(COMPRESSED_STREAM_TOOLS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NbtCompressionCodecService"));
        Assert.assertTrue(text.contains("NBT_COMPRESSION_CODEC_SERVICE.readCompressed"));
        Assert.assertTrue(text.contains("NBT_COMPRESSION_CODEC_SERVICE.writeCompressed"));
        Assert.assertTrue(text.contains("NBT_COMPRESSION_CODEC_SERVICE.readRootCompound"));
        Assert.assertTrue(text.contains("NBT_COMPRESSION_CODEC_SERVICE.writeRootCompound"));
        Assert.assertFalse(text.contains("new GZIPInputStream(inputstream)"));
        Assert.assertFalse(text.contains("new GZIPOutputStream(outputstream)"));
        Assert.assertFalse(text.contains("Root tag must be a named compound tag"));
    }
}
