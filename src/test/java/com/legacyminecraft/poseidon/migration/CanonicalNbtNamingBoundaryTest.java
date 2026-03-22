package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalNbtNamingBoundaryTest {
    private static final Path NBT_COMPRESSION_CODEC_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtCompressionCodec.java");
    private static final Path NBT_PRIMITIVE_CODEC_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtPrimitiveCodec.java");
    private static final Path NBT_COLLECTION_CODEC_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtCollectionCodec.java");
    private static final Path NBT_TYPE_REGISTRY_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtTypeRegistry.java");

    private static final Path NBT_COMPRESSION_CODEC_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtCompressionCodecService.java");
    private static final Path NBT_PRIMITIVE_CODEC_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtPrimitiveCodecService.java");
    private static final Path NBT_COLLECTION_CODEC_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtCollectionCodecService.java");
    private static final Path NBT_TYPE_REGISTRY_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/nbt/NbtTypeRegistryService.java");

    @Test
    public void canonicalNbtUsesCodecAndRegistryNamesWithDeprecatedServiceShims() throws IOException {
        String compressionCodecText = read(NBT_COMPRESSION_CODEC_PATH);
        String primitiveCodecText = read(NBT_PRIMITIVE_CODEC_PATH);
        String collectionCodecText = read(NBT_COLLECTION_CODEC_PATH);
        String typeRegistryText = read(NBT_TYPE_REGISTRY_PATH);

        Assert.assertTrue(compressionCodecText.contains("class NbtCompressionCodec"));
        Assert.assertTrue(primitiveCodecText.contains("class NbtPrimitiveCodec"));
        Assert.assertTrue(collectionCodecText.contains("class NbtCollectionCodec"));
        Assert.assertTrue(typeRegistryText.contains("class NbtTypeRegistry"));

        assertDeprecatedShim(read(NBT_COMPRESSION_CODEC_SERVICE_PATH), "NbtCompressionCodec");
        assertDeprecatedShim(read(NBT_PRIMITIVE_CODEC_SERVICE_PATH), "NbtPrimitiveCodec");
        assertDeprecatedShim(read(NBT_COLLECTION_CODEC_SERVICE_PATH), "NbtCollectionCodec");
        assertDeprecatedShim(read(NBT_TYPE_REGISTRY_SERVICE_PATH), "NbtTypeRegistry");
    }

    private static void assertDeprecatedShim(String serviceText, String canonicalTypeName) {
        Assert.assertTrue(serviceText.contains("@Deprecated"));
        Assert.assertTrue(serviceText.contains(canonicalTypeName));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
