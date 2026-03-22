package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyBlockTextureWrapperThinnessTest {
    private static final Path BED_BLOCK_TEXTURES_PATH = Paths.get("src/main/java/net/minecraft/server/BedBlockTextures.java");
    private static final Path PISTON_BLOCK_TEXTURES_PATH = Paths.get("src/main/java/net/minecraft/server/PistonBlockTextures.java");

    @Test
    public void blockTextureLookupWrappersDelegateToCanonicalBehaviours() throws IOException {
        String bedTextures = new String(Files.readAllBytes(BED_BLOCK_TEXTURES_PATH), StandardCharsets.UTF_8);
        String pistonTextures = new String(Files.readAllBytes(PISTON_BLOCK_TEXTURES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(bedTextures.contains("BedTextureOrientationBehaviour"));
        Assert.assertTrue(bedTextures.contains("BED_TEXTURE_ORIENTATION_BEHAVIOUR.getHeadAndFootFaces"));
        Assert.assertTrue(bedTextures.contains("BED_TEXTURE_ORIENTATION_BEHAVIOUR.getRotationTextureMap"));
        Assert.assertFalse(bedTextures.contains("new int[] { 3, 4, 2, 5}"));

        Assert.assertTrue(pistonTextures.contains("PistonTextureOrientationBehaviour"));
        Assert.assertTrue(pistonTextures.contains("PISTON_TEXTURE_ORIENTATION_BEHAVIOUR.getFaceRotationMap"));
        Assert.assertTrue(pistonTextures.contains("PISTON_TEXTURE_ORIENTATION_BEHAVIOUR.getZOffsets"));
        Assert.assertFalse(pistonTextures.contains("new int[] { 1, 0, 3, 2, 5, 4}"));
    }
}
