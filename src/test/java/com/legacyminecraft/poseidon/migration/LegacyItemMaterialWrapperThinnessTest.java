package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyItemMaterialWrapperThinnessTest {
    private static final Path ENUM_TOOL_MATERIAL_PATH = Paths.get("src/main/java/net/minecraft/server/EnumToolMaterial.java");

    @Test
    public void enumToolMaterialDelegatesPropertyAccessToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENUM_TOOL_MATERIAL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ToolMaterialBehaviour"));
        Assert.assertTrue(text.contains("TOOL_MATERIAL_BEHAVIOUR.getDurability"));
        Assert.assertTrue(text.contains("TOOL_MATERIAL_BEHAVIOUR.getMiningSpeed"));
        Assert.assertTrue(text.contains("TOOL_MATERIAL_BEHAVIOUR.getAttackDamage"));
        Assert.assertTrue(text.contains("TOOL_MATERIAL_BEHAVIOUR.getHarvestLevel"));
        Assert.assertFalse(text.contains("return this.g;"));
        Assert.assertFalse(text.contains("return this.h;"));
    }
}
