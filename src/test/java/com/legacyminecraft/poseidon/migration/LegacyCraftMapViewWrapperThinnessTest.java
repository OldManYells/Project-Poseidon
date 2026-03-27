package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftMapViewWrapperThinnessTest {
    private static final Path CRAFT_MAP_VIEW_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/map/CraftMapView.java");
    private static final Path CRAFT_MAP_VIEW_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftMapViewBehaviour.java");

    @Test
    public void craftMapViewDelegatesMapStateAndRendererLifecycleToCanonicalBehaviour() throws IOException {
        String text = read(CRAFT_MAP_VIEW_PATH);

        Assert.assertTrue(text.contains("CraftMapViewBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_MAP_VIEW_BEHAVIOUR"));
        Assert.assertTrue(text.contains("addDefaultRenderer(this, worldMap, renderers, canvases)"));
        Assert.assertTrue(text.contains("getId(worldMap.a)"));
        Assert.assertTrue(text.contains("isVirtual(renderers)"));
        Assert.assertTrue(text.contains("getScale(worldMap.e)"));
        Assert.assertTrue(text.contains("setScale(worldMap, scale)"));
        Assert.assertTrue(text.contains("getWorld(worldMap.map, Bukkit.getServer().getWorlds())"));
        Assert.assertTrue(text.contains("setWorld(worldMap, world)"));
        Assert.assertTrue(text.contains("getCenterX(worldMap)"));
        Assert.assertTrue(text.contains("getCenterZ(worldMap)"));
        Assert.assertTrue(text.contains("setCenterX(worldMap, x)"));
        Assert.assertTrue(text.contains("setCenterZ(worldMap, z)"));
        Assert.assertTrue(text.contains("copyRenderers(renderers)"));
        Assert.assertTrue(text.contains("addRenderer(this, renderers, canvases, renderer)"));
        Assert.assertTrue(text.contains("removeRenderer(renderers, canvases, renderer)"));
        Assert.assertTrue(text.contains("render(this, renderCache, renderers, canvases, player)"));
        Assert.assertFalse(text.contains("Arrays.fill(render.buffer, (byte) 0);"));
        Assert.assertFalse(text.contains("render.cursors.clear();"));
        Assert.assertFalse(text.contains("for (MapRenderer renderer : renderers)"));
        Assert.assertFalse(text.contains("canvas.setBase(render.buffer);"));
        Assert.assertFalse(text.contains("canvas.getBuffer();"));
        Assert.assertFalse(text.contains("new CraftMapCanvas(this);"));
        Assert.assertFalse(text.contains("new CraftMapRenderer(this, worldMap)"));
    }

    @Test
    public void craftMapViewBehaviourOwnsCanvasAndRenderCacheOrchestration() throws IOException {
        String text = read(CRAFT_MAP_VIEW_BEHAVIOUR_PATH);

        Assert.assertTrue(text.contains("CraftMapViewBehaviour"));
        Assert.assertTrue(text.contains("Arrays.fill(render.buffer, (byte) 0);"));
        Assert.assertTrue(text.contains("render.cursors.clear();"));
        Assert.assertTrue(text.contains("new org.bukkit.craftbukkit.map.CraftMapRenderer(mapView, worldMap)"));
        Assert.assertTrue(text.contains("createCanvas(mapView)"));
        Assert.assertTrue(text.contains("setBase(canvas, render.buffer);"));
        Assert.assertTrue(text.contains("renderer.render(mapView, canvas, player);"));
        Assert.assertTrue(text.contains("getBuffer(canvas);"));
        Assert.assertTrue(text.contains("clearCanvas(entry.getValue());"));
        Assert.assertTrue(text.contains("canvas.setPixel(x, y, (byte) -1);"));
        Assert.assertTrue(text.contains("MapCursor cursor = canvas.getCursors().getCursor(i);"));
        Assert.assertTrue(text.contains("WorldHandleBridgeBehaviour"));
        Assert.assertTrue(text.contains("WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveDimension(world)"));
        Assert.assertFalse(text.contains("render(this, renderCache, renderers, canvases, player)"));
        Assert.assertFalse(text.contains("((org.bukkit.craftbukkit.CraftWorld) world).getHandle().dimension"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
