package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEnumBoundaryTest {
    private static final Path ENUM_ART_PATH = Paths.get("src/main/java/net/minecraft/server/EnumArt.java");
    private static final Path ENUM_BED_ERROR_PATH = Paths.get("src/main/java/net/minecraft/server/EnumBedError.java");
    private static final Path ENUM_CREATURE_TYPE_PATH = Paths.get("src/main/java/net/minecraft/server/EnumCreatureType.java");
    private static final Path ENUM_MOB_TYPE_PATH = Paths.get("src/main/java/net/minecraft/server/EnumMobType.java");
    private static final Path ENUM_MOVING_OBJECT_TYPE_PATH = Paths.get("src/main/java/net/minecraft/server/EnumMovingObjectType.java");
    private static final Path ENUM_SKY_BLOCK_PATH = Paths.get("src/main/java/net/minecraft/server/EnumSkyBlock.java");

    @Test
    public void legacyEnumsImplementCanonicalContracts() throws IOException {
        String enumArtText = new String(Files.readAllBytes(ENUM_ART_PATH), StandardCharsets.UTF_8);
        String enumBedErrorText = new String(Files.readAllBytes(ENUM_BED_ERROR_PATH), StandardCharsets.UTF_8);
        String enumCreatureTypeText = new String(Files.readAllBytes(ENUM_CREATURE_TYPE_PATH), StandardCharsets.UTF_8);
        String enumMobTypeText = new String(Files.readAllBytes(ENUM_MOB_TYPE_PATH), StandardCharsets.UTF_8);
        String enumMovingObjectTypeText = new String(Files.readAllBytes(ENUM_MOVING_OBJECT_TYPE_PATH), StandardCharsets.UTF_8);
        String enumSkyBlockText = new String(Files.readAllBytes(ENUM_SKY_BLOCK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(enumArtText.contains("import com.legacyminecraft.poseidon.world.types.PaintingArtTypeContract;"));
        Assert.assertTrue(enumArtText.contains("public enum EnumArt implements PaintingArtTypeContract"));
        Assert.assertTrue(enumArtText.contains("public String getArtKey()"));
        Assert.assertTrue(enumArtText.contains("public int getPixelWidth()"));
        Assert.assertTrue(enumArtText.contains("public int getPixelHeight()"));
        Assert.assertTrue(enumArtText.contains("public int getTextureU()"));
        Assert.assertTrue(enumArtText.contains("public int getTextureV()"));

        Assert.assertTrue(enumBedErrorText.contains("import com.legacyminecraft.poseidon.world.types.BedErrorTypeContract;"));
        Assert.assertTrue(enumBedErrorText.contains("public enum EnumBedError implements BedErrorTypeContract"));

        Assert.assertTrue(enumCreatureTypeText.contains("import com.legacyminecraft.poseidon.world.types.CreatureTypeContract;"));
        Assert.assertTrue(enumCreatureTypeText.contains("public enum EnumCreatureType implements CreatureTypeContract"));

        Assert.assertTrue(enumMobTypeText.contains("import com.legacyminecraft.poseidon.world.types.MobSelectionTypeContract;"));
        Assert.assertTrue(enumMobTypeText.contains("public enum EnumMobType implements MobSelectionTypeContract"));

        Assert.assertTrue(enumMovingObjectTypeText.contains("import com.legacyminecraft.poseidon.world.types.MovingObjectTypeContract;"));
        Assert.assertTrue(enumMovingObjectTypeText.contains("public enum EnumMovingObjectType implements MovingObjectTypeContract"));

        Assert.assertTrue(enumSkyBlockText.contains("import com.legacyminecraft.poseidon.world.types.SkyLightTypeContract;"));
        Assert.assertTrue(enumSkyBlockText.contains("public enum EnumSkyBlock implements SkyLightTypeContract"));
        Assert.assertTrue(enumSkyBlockText.contains("public int getDefaultLightValue()"));
    }
}
