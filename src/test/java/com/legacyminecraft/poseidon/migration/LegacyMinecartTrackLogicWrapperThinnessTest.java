package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyMinecartTrackLogicWrapperThinnessTest {
    private static final Path MINECART_TRACK_LOGIC_PATH = Paths.get("src/main/java/net/minecraft/server/MinecartTrackLogic.java");

    @Test
    public void minecartTrackLogicDelegatesLayoutAndAdjacencyQueriesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(MINECART_TRACK_LOGIC_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MinecartTrackConnectionLayoutBehaviour"));
        Assert.assertTrue(text.contains("MinecartTrackPropagationBehaviour"));
        Assert.assertTrue(text.contains("MinecartTrackShapeSelectionBehaviour"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.populateConnections"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.hasAdjacentTrack"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.countAdjacentTracks"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.resolveTrackY"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.containsConnectionAt"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.canAcceptConnection"));
        Assert.assertTrue(text.contains("MINECART_TRACK_CONNECTION_LAYOUT_BEHAVIOUR.pruneDisconnectedConnections"));
        Assert.assertTrue(text.contains("MINECART_TRACK_PROPAGATION_BEHAVIOUR.resolvePropagationShape"));
        Assert.assertTrue(text.contains("MINECART_TRACK_PROPAGATION_BEHAVIOUR.resolvePlacementShape"));
        Assert.assertTrue(text.contains("MINECART_TRACK_PROPAGATION_BEHAVIOUR.forEachConnection"));
        Assert.assertTrue(text.contains("MINECART_TRACK_PROPAGATION_BEHAVIOUR.appendConnection"));
        Assert.assertFalse(text.contains("this.g.clear();"));
        Assert.assertFalse(text.contains("if (i == 0) {"));
        Assert.assertFalse(text.contains("return BlockMinecartTrack.g(this.b, chunkposition.x, chunkposition.y, chunkposition.z) ?"));
        Assert.assertFalse(text.contains("if (chunkposition.x == i && chunkposition.z == k)"));
        Assert.assertFalse(text.contains("ChunkPosition chunkposition = (ChunkPosition) this.g.get(0);"));
        Assert.assertFalse(text.contains("MinecartTrackLogic minecarttracklogic = this.a((ChunkPosition) this.g.get(j));"));
        Assert.assertFalse(text.contains("if (flag3 && flag5 && !flag2 && !flag4)"));
        Assert.assertFalse(text.contains("if (flag || flag1)"));
    }
}
