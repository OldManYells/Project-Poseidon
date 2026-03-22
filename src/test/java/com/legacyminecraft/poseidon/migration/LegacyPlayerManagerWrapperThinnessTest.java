package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyPlayerManagerWrapperThinnessTest {
    private static final Path PLAYER_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerManager.java");

    @Test
    public void playerManagerDelegatesViewRangePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerViewRangeBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkCoordinateBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkIterationBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkSpiralLoadBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkMovementBehaviour;"));
        Assert.assertTrue(text.contains("PLAYER_VIEW_RANGE_BEHAVIOUR.validateViewRadius(j);"));
        Assert.assertTrue(text.contains("PLAYER_VIEW_RANGE_BEHAVIOUR.isWithinViewRange(i, j, k, l, this.f)"));
        Assert.assertTrue(text.contains("PLAYER_VIEW_RANGE_BEHAVIOUR.furthestViewableBlock(this.f)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromBlock(i)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_COORDINATE_BEHAVIOUR.localBlockInChunk(i)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromWorldPosition(entityplayer.locX)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_COORDINATE_BEHAVIOUR.chunkFromWorldPosition(entityplayer.d)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_ITERATION_BEHAVIOUR.forEachChunkInViewRange(i, j, this.f"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_SPIRAL_LOAD_BEHAVIOUR.forEachSpiralChunk(i, j, this.f"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.chunkCoordinate(entityplayer.locX)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.squaredMovement(entityplayer.d, entityplayer.e, entityplayer.locX, entityplayer.locZ)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MOVEMENT_BEHAVIOUR.shouldProcessChunkMovement(d2)"));
        Assert.assertFalse(text.contains("throw new IllegalArgumentException(\"Too big view radius!\")"));
        Assert.assertFalse(text.contains("return i1 >= -this.f && i1 <= this.f ?"));
        Assert.assertFalse(text.contains("double d0 = entityplayer.d - entityplayer.locX;"));
        Assert.assertFalse(text.contains("int l = i >> 4;"));
        Assert.assertFalse(text.contains("playerinstance.a(i & 15, j, k & 15);"));
        Assert.assertFalse(text.contains("for (int k = i - this.f; k <= i + this.f; ++k)"));
        Assert.assertFalse(text.contains("int[] aint = this.g[k++ % 4];"));
        Assert.assertFalse(text.contains("return this.f * 16 - 16;"));
    }
}
