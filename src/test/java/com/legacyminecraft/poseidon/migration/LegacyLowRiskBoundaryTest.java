package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyLowRiskBoundaryTest {
    private static final Path MATERIAL_MAP_COLOR_PATH = Paths.get("src/main/java/net/minecraft/server/MaterialMapColor.java");
    private static final Path MINECRAFT_EXCEPTION_PATH = Paths.get("src/main/java/net/minecraft/server/MinecraftException.java");
    private static final Path EMPTY_CLASS_1_PATH = Paths.get("src/main/java/net/minecraft/server/EmptyClass1.java");
    private static final Path EMPTY_CLASS_2_PATH = Paths.get("src/main/java/net/minecraft/server/EmptyClass2.java");

    @Test
    public void legacyLowRiskDefinitionsBridgeToCanonicalTypes() throws IOException {
        String materialMapColorText = new String(Files.readAllBytes(MATERIAL_MAP_COLOR_PATH), StandardCharsets.UTF_8);
        String minecraftExceptionText = new String(Files.readAllBytes(MINECRAFT_EXCEPTION_PATH), StandardCharsets.UTF_8);
        String emptyClass1Text = new String(Files.readAllBytes(EMPTY_CLASS_1_PATH), StandardCharsets.UTF_8);
        String emptyClass2Text = new String(Files.readAllBytes(EMPTY_CLASS_2_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(materialMapColorText.contains("import com.legacyminecraft.poseidon.block.MaterialMapColorStateBehaviour;"));
        Assert.assertTrue(materialMapColorText.contains("import com.legacyminecraft.poseidon.world.types.MapColorTypeContract;"));
        Assert.assertTrue(materialMapColorText.contains("public class MaterialMapColor implements MapColorTypeContract"));
        Assert.assertTrue(materialMapColorText.contains("MATERIAL_MAP_COLOR_STATE_BEHAVIOUR.initialize(a, i, j, this)"));
        Assert.assertTrue(materialMapColorText.contains("public int getColorRgb()"));
        Assert.assertTrue(materialMapColorText.contains("public int getColorIndex()"));
        Assert.assertFalse(materialMapColorText.contains("a[i] = this;"));

        Assert.assertTrue(minecraftExceptionText.contains("import com.legacyminecraft.poseidon.runtime.PoseidonRuntimeException;"));
        Assert.assertTrue(minecraftExceptionText.contains("public class MinecraftException extends PoseidonRuntimeException"));

        Assert.assertTrue(emptyClass1Text.contains("import com.legacyminecraft.poseidon.compat.nms.LegacyEmptyShimContract;"));
        Assert.assertTrue(emptyClass1Text.contains("class EmptyClass1 implements LegacyEmptyShimContract"));

        Assert.assertTrue(emptyClass2Text.contains("import com.legacyminecraft.poseidon.compat.nms.LegacyEmptyShimContract;"));
        Assert.assertTrue(emptyClass2Text.contains("class EmptyClass2 implements LegacyEmptyShimContract"));
    }
}
