package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkWrapperThinnessTest {
    private static final Path CHUNK_PATH = Paths.get("src/main/java/net/minecraft/server/Chunk.java");

    @Test
    public void chunkDelegatesBukkitWrapperCreationAndCrossChunkPlayerCleanupToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ChunkEntitySliceCleanupBehaviour"));
        Assert.assertTrue(text.contains("ChunkEntityLifecycleBehaviour"));
        Assert.assertTrue(text.contains("ChunkEntitySliceIndexBehaviour"));
        Assert.assertTrue(text.contains("ChunkEntityQueryBehaviour"));
        Assert.assertTrue(text.contains("ChunkDataExtractionBehaviour"));
        Assert.assertTrue(text.contains("ChunkLightAccessBehaviour"));
        Assert.assertTrue(text.contains("ChunkLightingInitializationBehaviour"));
        Assert.assertTrue(text.contains("ChunkStateAccessBehaviour"));
        Assert.assertTrue(text.contains("ChunkBlockMutationBehaviour"));
        Assert.assertTrue(text.contains("ChunkSkyLightColumnUpdateBehaviour"));
        Assert.assertTrue(text.contains("ChunkSaveDecisionBehaviour"));
        Assert.assertTrue(text.contains("ChunkSeededRandomBehaviour"));
        Assert.assertTrue(text.contains("ChunkTileEntityLifecycleBehaviour"));
        Assert.assertTrue(text.contains("ChunkTileEntityLookupBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_CLEANUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_LIFECYCLE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_QUERY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_DATA_EXTRACTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_LIGHT_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_LIGHTING_INITIALIZATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_STATE_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_BLOCK_MUTATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_SKY_LIGHT_COLUMN_UPDATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_SAVE_DECISION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_SEEDED_RANDOM_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LIFECYCLE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_CLEANUP_BEHAVIOUR.createBukkitChunk(this)"));
        Assert.assertTrue(text.contains(
                "CHUNK_ENTITY_SLICE_CLEANUP_BEHAVIOUR.shouldRemoveCrossChunkPlayerEntity(entity, chunkX, chunkZ)"
        ));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_LIFECYCLE_BEHAVIOUR.addEntities(this.world, this.tileEntities, this.entitySlices);"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_LIFECYCLE_BEHAVIOUR.removeEntities("));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_QUERY_BEHAVIOUR.resolveSliceBounds(axisalignedbb, this.entitySlices.length)"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_QUERY_BEHAVIOUR.collectCollidingExcluding(entity, axisalignedbb, this.entitySlices, list, bounds);"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_QUERY_BEHAVIOUR.collectAssignableColliding(oclass, axisalignedbb, this.entitySlices, list, bounds);"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.resolveChunkCoordinate(entity.locX)"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.resolveChunkCoordinate(entity.locZ)"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.resolveSliceIndex(entity.locY, this.entitySlices.length)"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.markAndAdd(entity, this.x, this.z, k, this.entitySlices);"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.clampSliceIndex(i, this.entitySlices.length)"));
        Assert.assertTrue(text.contains("CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.remove(entity, sliceIndex, this.entitySlices);"));
        Assert.assertTrue(text.contains("CHUNK_DATA_EXTRACTION_BEHAVIOUR.copyChunkData("));
        Assert.assertTrue(text.contains("CHUNK_LIGHT_ACCESS_BEHAVIOUR.getLight(this.f, this.g, enumskyblock, i, j, k)"));
        Assert.assertTrue(text.contains("CHUNK_LIGHT_ACCESS_BEHAVIOUR.setLight(this.f, this.g, enumskyblock, i, j, k, l);"));
        Assert.assertTrue(text.contains("CHUNK_LIGHT_ACCESS_BEHAVIOUR.resolveBrightness(this.f, this.g, i, j, k, l)"));
        Assert.assertTrue(text.contains("CHUNK_STATE_ACCESS_BEHAVIOUR.isChunkCoordinates(i, j, this.x, this.z)"));
        Assert.assertTrue(text.contains("CHUNK_STATE_ACCESS_BEHAVIOUR.getHeightAt(this.heightMap, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_STATE_ACCESS_BEHAVIOUR.getMetadata(this.e, i, j, k)"));
        Assert.assertTrue(text.contains("CHUNK_STATE_ACCESS_BEHAVIOUR.setMetadata(this.e, i, j, k, l);"));
        Assert.assertTrue(text.contains("CHUNK_STATE_ACCESS_BEHAVIOUR.isAtOrAboveHeight(this.heightMap, i, j, k)"));
        Assert.assertTrue(text.contains("CHUNK_BLOCK_MUTATION_BEHAVIOUR.setTypeAndData("));
        Assert.assertTrue(text.contains("CHUNK_BLOCK_MUTATION_BEHAVIOUR.setType("));
        Assert.assertTrue(text.contains("CHUNK_SKY_LIGHT_COLUMN_UPDATE_BEHAVIOUR.relightNeighborColumns("));
        Assert.assertTrue(text.contains("CHUNK_SKY_LIGHT_COLUMN_UPDATE_BEHAVIOUR.updateColumn("));
        Assert.assertTrue(text.contains("CHUNK_LIGHTING_INITIALIZATION_BEHAVIOUR.initLighting(this.world, this.b, this.heightMap, this.f)"));
        Assert.assertTrue(text.contains("WorldFeatureConfigPolicy"));
        Assert.assertTrue(text.contains("WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledKey()"));
        Assert.assertTrue(text.contains("WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledDefault()"));
        Assert.assertTrue(text.contains("CHUNK_SAVE_DECISION_BEHAVIOUR.shouldSave(this.p, this.q, this.world.getTime(), this.r, flag, this.o)"));
        Assert.assertTrue(text.contains("CHUNK_SEEDED_RANDOM_BEHAVIOUR.create(this.world.getSeed(), this.x, this.z, i)"));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LIFECYCLE_BEHAVIOUR.placeTileEntity("));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LIFECYCLE_BEHAVIOUR.removeTileEntityIfActive(this.tileEntities, this.c, i, j, k);"));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.resolveFromCache(this.tileEntities, chunkposition)"));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.shouldCreateForType(l)"));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.createTileEntity("));
        Assert.assertTrue(text.contains("CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.pruneInvalidTileEntity(this.tileEntities, chunkposition, tileentity)"));

        Assert.assertFalse(text.contains("new org.bukkit.craftbukkit.CraftChunk(this)"));
        Assert.assertFalse(text.contains("org.bukkit.Location.locToBlock(entity.locX) >> 4"));
        Assert.assertFalse(text.contains("org.bukkit.Location.locToBlock(entity.locZ) >> 4"));
        Assert.assertFalse(text.contains("this.world.a(this.tileEntities.values());"));
        Assert.assertFalse(text.contains("this.world.a(this.entitySlices[i]);"));
        Assert.assertFalse(text.contains("world.markForRemoval(tileentity);"));
        Assert.assertFalse(text.contains("this.world.b(this.entitySlices[i]);"));
        Assert.assertFalse(text.contains("tileentity.world = this.world;"));
        Assert.assertFalse(text.contains("tileentity.j();"));
        Assert.assertFalse(text.contains("System.out.println(\"Attempted to place a tile entity where there was no entity tile!\");"));
        Assert.assertFalse(text.contains("TileEntity tileentity = (TileEntity) this.tileEntities.remove(chunkposition);"));
        Assert.assertFalse(text.contains("if (!Block.isTileEntity[l])"));
        Assert.assertFalse(text.contains("blockcontainer.c(this.world, this.x * 16 + i, j, this.z * 16 + k);"));
        Assert.assertFalse(text.contains("if (tileentity != null && tileentity.g())"));
        Assert.assertFalse(text.contains("if (j >= this.entitySlices.length)"));
        Assert.assertFalse(text.contains("if (entity1 != entity && entity1.boundingBox.a(axisalignedbb))"));
        Assert.assertFalse(text.contains("if (oclass.isAssignableFrom(entity.getClass()) && entity.boundingBox.a(axisalignedbb))"));
        Assert.assertFalse(text.contains("if (this.p) {"));
        Assert.assertFalse(text.contains("if (this.q && this.world.getTime() != this.r)"));
        Assert.assertFalse(text.contains("if (this.q && this.world.getTime() >= this.r + 600L)"));
        Assert.assertFalse(text.contains("new Random(this.world.getSeed() + (long) (this.x * this.x * 4987142)"));
        Assert.assertFalse(text.contains("if (l1 * i2 * j2 == this.b.length)"));
        Assert.assertFalse(text.contains("System.arraycopy(this.b, 0, abyte, k1, this.b.length);"));
        Assert.assertFalse(text.contains("return enumskyblock == EnumSkyBlock.SKY ? this.f.a(i, j, k) : (enumskyblock == EnumSkyBlock.BLOCK ? this.g.a(i, j, k) : 0);"));
        Assert.assertFalse(text.contains("if (enumskyblock == EnumSkyBlock.SKY)"));
        Assert.assertFalse(text.contains("i1 -= l;"));
        Assert.assertFalse(text.contains("if (j1 > i1)"));
        Assert.assertFalse(text.contains("return i == this.x && j == this.z;"));
        Assert.assertFalse(text.contains("return this.heightMap[j << 4 | i] & 255;"));
        Assert.assertFalse(text.contains("return this.e.a(i, j, k);"));
        Assert.assertFalse(text.contains("this.e.a(i, j, k, l);"));
        Assert.assertFalse(text.contains("byte b0 = (byte) l;"));
        Assert.assertFalse(text.contains("this.b[i << 11 | k << 7 | j] = (byte) (b0 & 255);"));
        Assert.assertFalse(text.contains("Block.byId[k1].remove(this.world, l1, j, i2);"));
        Assert.assertFalse(text.contains("this.world.a(EnumSkyBlock.BLOCK, l1, j, i2, l1, j, i2);"));
        Assert.assertFalse(text.contains("this.f(l - 1, i1, k);"));
        Assert.assertFalse(text.contains("int l = this.world.getHighestBlockYAt(i, j);"));
        Assert.assertFalse(text.contains("for (j = 0; j < 16; ++j)"));
        Assert.assertFalse(text.contains("this.heightMap[k << 4 | j] = (byte) l;"));
        Assert.assertFalse(text.contains("this.f.a(j, k1, k, j1);"));
        Assert.assertFalse(text.contains("entity.bG = true;"));
        Assert.assertFalse(text.contains("this.entitySlices[k].add(entity);"));
    }
}
