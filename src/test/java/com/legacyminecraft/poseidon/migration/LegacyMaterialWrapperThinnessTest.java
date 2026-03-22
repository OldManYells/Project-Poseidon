package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyMaterialWrapperThinnessTest {
    private static final Path MATERIAL_PATH = Paths.get("src/main/java/net/minecraft/server/Material.java");
    private static final Path MATERIAL_LIQUID_PATH = Paths.get("src/main/java/net/minecraft/server/MaterialLiquid.java");
    private static final Path MATERIAL_LOGIC_PATH = Paths.get("src/main/java/net/minecraft/server/MaterialLogic.java");
    private static final Path MATERIAL_PORTAL_PATH = Paths.get("src/main/java/net/minecraft/server/MaterialPortal.java");
    private static final Path MATERIAL_TRANSPARENT_PATH = Paths.get("src/main/java/net/minecraft/server/MaterialTransparent.java");

    @Test
    public void materialWrappersDelegatePropertyQueriesToCanonicalBehaviours() throws IOException {
        String material = new String(Files.readAllBytes(MATERIAL_PATH), StandardCharsets.UTF_8);
        String liquid = new String(Files.readAllBytes(MATERIAL_LIQUID_PATH), StandardCharsets.UTF_8);
        String logic = new String(Files.readAllBytes(MATERIAL_LOGIC_PATH), StandardCharsets.UTF_8);
        String portal = new String(Files.readAllBytes(MATERIAL_PORTAL_PATH), StandardCharsets.UTF_8);
        String transparent = new String(Files.readAllBytes(MATERIAL_TRANSPARENT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(material.contains("MaterialPropertyBehaviour"));
        Assert.assertTrue(material.contains("MATERIAL_PROPERTY_BEHAVIOUR.isBurnable"));
        Assert.assertTrue(material.contains("MATERIAL_PROPERTY_BEHAVIOUR.blocksMovement"));
        Assert.assertFalse(material.contains("return this.F ? false : this.isSolid();"));

        Assert.assertTrue(liquid.contains("LiquidMaterialBehaviour"));
        Assert.assertTrue(liquid.contains("NonSolidMaterialBehaviour"));
        Assert.assertTrue(liquid.contains("LIQUID_MATERIAL_BEHAVIOUR.isLiquid"));
        Assert.assertFalse(liquid.contains("return true;"));

        Assert.assertTrue(logic.contains("NonSolidMaterialBehaviour"));
        Assert.assertTrue(logic.contains("NON_SOLID_MATERIAL_BEHAVIOUR.isBuildable"));
        Assert.assertFalse(logic.contains("return false;"));

        Assert.assertTrue(portal.contains("NonSolidMaterialBehaviour"));
        Assert.assertTrue(portal.contains("NON_SOLID_MATERIAL_BEHAVIOUR.blocksLight"));
        Assert.assertFalse(portal.contains("return false;"));

        Assert.assertTrue(transparent.contains("NonSolidMaterialBehaviour"));
        Assert.assertTrue(transparent.contains("NON_SOLID_MATERIAL_BEHAVIOUR.isSolid"));
        Assert.assertFalse(transparent.contains("return false;"));
    }
}
