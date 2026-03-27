package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftMapCanvasRendererWrapperThinnessTest {
    private static final Path CRAFT_MAP_CANVAS_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/map/CraftMapCanvas.java");
    private static final Path CRAFT_MAP_RENDERER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/map/CraftMapRenderer.java");
    private static final Path RENDER_DATA_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/map/RenderData.java");

    @Test
    public void craftMapCanvasDelegatesCanvasLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_MAP_CANVAS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftMapCanvasBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_MAP_CANVAS_BEHAVIOUR.initializeBuffer"));
        Assert.assertTrue(text.contains("CRAFT_MAP_CANVAS_BEHAVIOUR.setPixel"));
        Assert.assertTrue(text.contains("CRAFT_MAP_CANVAS_BEHAVIOUR.getPixel"));
        Assert.assertTrue(text.contains("CRAFT_MAP_CANVAS_BEHAVIOUR.getBasePixel"));
        Assert.assertTrue(text.contains("CRAFT_MAP_CANVAS_BEHAVIOUR.drawImage"));
        Assert.assertTrue(text.contains("CRAFT_MAP_CANVAS_BEHAVIOUR.drawText"));
        Assert.assertFalse(text.contains("Arrays.fill(buffer, (byte) -1)"));
        Assert.assertFalse(text.contains("if (x < 0 || y < 0 || x >= 128 || y >= 128)"));
        Assert.assertFalse(text.contains("MapPalette.imageToBytes(image)"));
        Assert.assertFalse(text.contains("CharacterSprite sprite = font.getChar(text.charAt(i))"));
    }

    @Test
    public void craftMapRendererDelegatesMapAndCursorSyncToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_MAP_RENDERER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftMapRendererBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_MAP_RENDERER_BEHAVIOUR.render"));
        Assert.assertFalse(text.contains("canvas.setPixel(x, y, worldMap.f[y * 128 + x])"));
        Assert.assertFalse(text.contains("MapCursorCollection cursors = canvas.getCursors()"));
        Assert.assertFalse(text.contains("WorldMapOrienter orienter = (WorldMapOrienter) worldMap.i.get(i)"));
    }

    @Test
    public void renderDataDelegatesAllocationToCanonicalFactoryBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(RENDER_DATA_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftMapRenderDataFactoryBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_MAP_RENDER_DATA_FACTORY_BEHAVIOUR.createBuffer"));
        Assert.assertTrue(text.contains("CRAFT_MAP_RENDER_DATA_FACTORY_BEHAVIOUR.createCursors"));
        Assert.assertFalse(text.contains("new byte[128 * 128]"));
        Assert.assertFalse(text.contains("new ArrayList<MapCursor>()"));
    }
}
