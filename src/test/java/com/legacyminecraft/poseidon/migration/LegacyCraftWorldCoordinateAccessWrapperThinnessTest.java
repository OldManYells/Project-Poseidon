package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldCoordinateAccessWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldCoordinateAccessBehaviour.java");

    @Test
    public void craftWorldDelegatesCoordinateAccessorWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR_PATH);
        String coordinateSection = section(craftWorldText, "public Block getBlockAt(int x, int y, int z) {", "public Location getSpawnLocation() {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldCoordinateAccessBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR.getBlockAt(this, x, y, z)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR.getBlockTypeIdAt(world, x, y, z)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR.getHighestBlockYAt(world, x, z)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR.getChunkAt(world, x, z)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_COORDINATE_ACCESS_BEHAVIOUR.getChunkAt(this, block)"));

        Assert.assertFalse(coordinateSection.contains("return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0x7F, z & 0xF);"));
        Assert.assertFalse(coordinateSection.contains("return world.getTypeId(x, y, z);"));
        Assert.assertFalse(coordinateSection.contains("return world.getHighestBlockYAt(x, z);"));
        Assert.assertFalse(coordinateSection.contains("return this.world.chunkProviderServer.getChunkAt(x, z).bukkitChunk;"));
        Assert.assertFalse(coordinateSection.contains("return getChunkAt(block.getX() >> 4, block.getZ() >> 4);"));

        Assert.assertTrue(behaviourText.contains("getBlockAt(CraftWorld craftWorld, int x, int y, int z)"));
        Assert.assertTrue(behaviourText.contains("craftWorld.getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0x7F, z & 0xF)"));
        Assert.assertTrue(behaviourText.contains("getBlockTypeIdAt(WorldServer worldServer, int x, int y, int z)"));
        Assert.assertTrue(behaviourText.contains("worldServer.getTypeId(x, y, z)"));
        Assert.assertTrue(behaviourText.contains("getHighestBlockYAt(WorldServer worldServer, int x, int z)"));
        Assert.assertTrue(behaviourText.contains("worldServer.getHighestBlockYAt(x, z)"));
        Assert.assertTrue(behaviourText.contains("getChunkAt(WorldServer worldServer, int x, int z)"));
        Assert.assertTrue(behaviourText.contains("worldServer.chunkProviderServer.getChunkAt(x, z).bukkitChunk"));
        Assert.assertTrue(behaviourText.contains("getChunkAt(CraftWorld craftWorld, Block block)"));
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
