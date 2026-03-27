package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldHandleAccessWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_HANDLE_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldHandleAccessBehaviour.java");

    @Test
    public void craftWorldDelegatesHandleAndTileEntityWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_HANDLE_ACCESS_BEHAVIOUR_PATH);
        String handleSection = section(craftWorldText, "public WorldServer getHandle() {", "public org.bukkit.entity.Item dropItem(Location loc, ItemStack item) {");
        String tileSection = section(craftWorldText, "public TileEntity getTileEntityAt(final int x, final int y, final int z) {", "public String getName() {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldHandleAccessBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_HANDLE_ACCESS_BEHAVIOUR.getHandle(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_HANDLE_ACCESS_BEHAVIOUR.getTileEntityAt(world, x, y, z)"));
        Assert.assertFalse(handleSection.contains("return world;"));
        Assert.assertFalse(tileSection.contains("return world.getTileEntity(x, y, z);"));

        Assert.assertTrue(behaviourText.contains("getHandle(WorldServer worldServer)"));
        Assert.assertTrue(behaviourText.contains("return worldServer;"));
        Assert.assertTrue(behaviourText.contains("getTileEntityAt(WorldServer worldServer, int x, int y, int z)"));
        Assert.assertTrue(behaviourText.contains("return worldServer.getTileEntity(x, y, z);"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
