package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyPlayerInstanceWrapperThinnessTest {
    private static final Path PLAYER_INSTANCE_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerInstance.java");

    @Test
    public void playerInstanceDelegatesDirtyBlockTrackingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_INSTANCE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkDirtyBlockBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkDirtyFlushBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkMembershipBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkPacketDispatchBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.player.PlayerChunkTileEntityPacketBehaviour;"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.min(this.h, i)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.max(this.i, i)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.encodeDirtyBlock(i, j, k)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.decodeLocalX(encodedDirtyBlock)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.decodeY(encodedDirtyBlock)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.decodeLocalZ(encodedDirtyBlock)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.containsDirtyBlock(this.dirtyBlocks, this.dirtyCount, encodedDirtyBlock)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_BLOCK_BEHAVIOUR.appendDirtyBlock(this.dirtyBlocks, this.dirtyCount, encodedDirtyBlock)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.isSingleBlockUpdate(this.dirtyCount)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.isFullChunkSectionUpdate(this.dirtyCount)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.worldCoordinate(this.chunkX, this.h)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.alignSectionMinY(this.j)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.alignSectionMaxY(this.k)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.sectionWidth(this.h, this.i)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.sectionHeight(this.j, this.k)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_DIRTY_FLUSH_BEHAVIOUR.sectionDepth(this.l, this.m)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.shouldSendPreChunkLoad(chunkSubscriptionAdded)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.isChunkNowEmpty(this.b.size())"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.chunkKey(this.chunkX, this.chunkZ)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.shouldUntrackDirtyInstance(this.dirtyCount)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_MEMBERSHIP_BEHAVIOUR.shouldSendPreChunkUnload(chunkSubscriptionRemoved)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_TILE_ENTITY_PACKET_BEHAVIOUR.extractUpdatePacket(tileentity)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_PACKET_DISPATCH_BEHAVIOUR.sendToSubscribedPlayers(this.b, this.location, packet);"));
        Assert.assertFalse(text.contains("short short1 = (short) (i << 12 | k << 8 | j);"));
        Assert.assertFalse(text.contains("this.dirtyBlocks[this.dirtyCount++] = short1;"));
        Assert.assertFalse(text.contains("this.dirtyBlocks[i] >> 12 & 15"));
        Assert.assertFalse(text.contains("this.dirtyBlocks[i] & 255"));
        Assert.assertFalse(text.contains("this.dirtyBlocks[i] >> 8 & 15"));
        Assert.assertFalse(text.contains("Packet packet = tileentity.f();"));
        Assert.assertFalse(text.contains("entityplayer.playerChunkCoordIntPairs.contains(this.location)"));
        Assert.assertFalse(text.contains("entityplayer.netServerHandler.sendPacket(packet);"));
        Assert.assertFalse(text.contains("if (this.dirtyCount == 1)"));
        Assert.assertFalse(text.contains("if (this.dirtyCount == 10)"));
        Assert.assertFalse(text.contains("this.j = this.j / 2 * 2;"));
        Assert.assertFalse(text.contains("this.k = (this.k / 2 + 1) * 2;"));
        Assert.assertFalse(text.contains("this.b.size() == 0"));
        Assert.assertFalse(text.contains("long i = (long) this.chunkX + 2147483647L | (long) this.chunkZ + 2147483647L << 32;"));
    }
}
