package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftChunkWrapperThinnessTest {
    private static final Path CRAFT_CHUNK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftChunk.java");
    private static final Path CRAFT_CHUNK_EMPTY_SNAPSHOT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftChunkEmptySnapshotBehaviour.java");
    private static final Path CRAFT_CHUNK_EMPTY_SNAPSHOT_FACTORY_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftChunkEmptySnapshotFactoryBehaviour.java");
    private static final Path CRAFT_CHUNK_SNAPSHOT_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftChunkSnapshotSystem.java");
    private static final Path CRAFT_CHUNK_INITIALIZATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftChunkInitializationBehaviour.java");
    private static final Path CRAFT_CHUNK_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftChunkAccessBehaviour.java");

    @Test
    public void craftChunkDelegatesCacheAndCollectionOperationsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHUNK_PATH), StandardCharsets.UTF_8);
        String chunkInitializationBehaviourText =
                new String(Files.readAllBytes(CRAFT_CHUNK_INITIALIZATION_BEHAVIOUR_PATH), StandardCharsets.UTF_8);
        String chunkAccessBehaviourText =
                new String(Files.readAllBytes(CRAFT_CHUNK_ACCESS_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftChunkAccessBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CraftChunkHandleResolutionBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_HANDLE_RESOLUTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveHandle(weakChunk, worldServer, x, z)"));
        Assert.assertTrue(text.contains("CraftChunkInitializationBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_INITIALIZATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("initialize(getHandle())"));
        Assert.assertTrue(text.contains("CraftChunkIdentityBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_IDENTITY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getWorld(worldServer)"));
        Assert.assertTrue(text.contains("getX(x)"));
        Assert.assertTrue(text.contains("getZ(z)"));
        Assert.assertTrue(text.contains("toString(getX(), getZ())"));
        Assert.assertTrue(text.contains("CraftChunkWeakLinkBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_WEAK_LINK_BEHAVIOUR"));
        Assert.assertTrue(text.contains("breakLink(weakChunk)"));
        Assert.assertTrue(text.contains("resolveBlock(this.cache, this, getX(), getZ(), x, y, z)"));
        Assert.assertTrue(text.contains("collectEntities(getHandle())"));
        Assert.assertTrue(text.contains("collectTileEntityStates(getHandle(), worldServer)"));
        Assert.assertTrue(text.contains("CraftChunkSnapshotModeBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_SNAPSHOT_MODE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("includeMaxBlockYByDefault()"));
        Assert.assertTrue(text.contains("includeBiomeByDefault()"));
        Assert.assertTrue(text.contains("includeBiomeClimateByDefault()"));
        Assert.assertTrue(text.contains("CraftChunkEmptySnapshotBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_EMPTY_SNAPSHOT_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createEmptySnapshot(world, x, z, includeBiome, includeBiomeTempRain)"));
        Assert.assertTrue(text.contains("CraftChunkEmptySnapshotFactoryBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_EMPTY_SNAPSHOT_FACTORY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createEmptyChunkSnapshot("));
        Assert.assertTrue(text.contains("CraftChunkLifecycleBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("isLoaded(getWorld(), getX(), getZ())"));
        Assert.assertTrue(text.contains("load(getWorld(), getX(), getZ(), true)"));
        Assert.assertTrue(text.contains("load(getWorld(), getX(), getZ(), generate)"));
        Assert.assertTrue(text.contains("unload(getWorld(), getX(), getZ())"));
        Assert.assertTrue(text.contains("unload(getWorld(), getX(), getZ(), save)"));
        Assert.assertTrue(text.contains("unload(getWorld(), getX(), getZ(), save, safe)"));
        Assert.assertTrue(text.contains("CraftChunkSnapshotSystem"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_SNAPSHOT_SYSTEM"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_SNAPSHOT_SYSTEM.createSnapshot("));
        Assert.assertTrue(text.contains("this,"));
        Assert.assertTrue(text.contains("includeMaxblocky,"));
        Assert.assertTrue(text.contains("includeBiome,"));
        Assert.assertTrue(text.contains("includeBiomeTempRain"));
        Assert.assertFalse(text.contains("int pos = (x & 0xF) << 11 | (z & 0xF) << 7 | (y & 0x7F);"));
        Assert.assertFalse(text.contains("for (int i = 0; i < 8; i++)"));
        Assert.assertFalse(text.contains("chunk.entitySlices[i].size()"));
        Assert.assertFalse(text.contains("chunk.tileEntities.keySet().toArray()"));
        Assert.assertFalse(text.contains("weakChunk.get()"));
        Assert.assertFalse(text.contains("worldServer.getChunkAt(x, z)"));
        Assert.assertFalse(text.contains("worldServer = (WorldServer) getHandle().world;"));
        Assert.assertFalse(text.contains("x = getHandle().x;"));
        Assert.assertFalse(text.contains("z = getHandle().z;"));
        Assert.assertFalse(text.contains("return worldServer.getWorld();"));
        Assert.assertFalse(text.contains("return x;"));
        Assert.assertFalse(text.contains("return z;"));
        Assert.assertFalse(text.contains("weakChunk.clear();"));
        Assert.assertFalse(text.contains("return \"CraftChunk{\" + \"x=\" + getX() + \"z=\" + getZ() + '}';"));
        Assert.assertFalse(text.contains("byte[] buf = new byte[32768 + 16384 + 16384 + 16384];"));
        Assert.assertFalse(text.contains("chunk.getData(buf, 0, 0, 0, 16, 128, 16, 0);"));
        Assert.assertFalse(text.contains("System.arraycopy(wcm.temperature, 0, biomeTemp, 0, biomeTemp.length);"));
        Assert.assertFalse(text.contains("return getChunkSnapshot(true, false, false);"));
        Assert.assertFalse(text.contains("captureEmpty("));
        Assert.assertFalse(text.contains("CraftChunkSnapshotCreationBehaviour"));
        Assert.assertFalse(text.contains("captureFromChunk("));
        Assert.assertFalse(text.contains("createSnapshot(getX(), getZ(), getWorld(), snapshotData)"));
        Assert.assertFalse(text.contains("world.getHandle().getWorldChunkManager()"));
        Assert.assertFalse(text.contains("return getWorld().isChunkLoaded(this);"));
        Assert.assertFalse(text.contains("return getWorld().loadChunk(getX(), getZ(), true);"));
        Assert.assertFalse(text.contains("return getWorld().unloadChunk(getX(), getZ(), save, safe);"));
        Assert.assertTrue(chunkInitializationBehaviourText.contains("WorldServerProjectionBridgeBehaviour"));
        Assert.assertTrue(chunkInitializationBehaviourText.contains("WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveWorldServer(chunkHandle.world)"));
        Assert.assertFalse(chunkInitializationBehaviourText.contains("new InitializationState((WorldServer) chunkHandle.world, chunkHandle.x, chunkHandle.z)"));
        Assert.assertTrue(chunkAccessBehaviourText.contains("NmsEntityProjectionBridgeBehaviour"));
        Assert.assertTrue(chunkAccessBehaviourText.contains("NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR.resolveBukkitEntity(entry)"));
        Assert.assertFalse(chunkAccessBehaviourText.contains("if (!(entry instanceof net.minecraft.server.Entity))"));
        Assert.assertFalse(chunkAccessBehaviourText.contains("((net.minecraft.server.Entity) entry).getBukkitEntity()"));
    }

    @Test
    public void emptyChunkSnapshotBehaviourOwnsCaptureOrchestrationAndFactoryDelegation() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHUNK_EMPTY_SNAPSHOT_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftChunkEmptySnapshotBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("captureEmpty("));
        Assert.assertTrue(text.contains("world.getHandle().getWorldChunkManager()"));
        Assert.assertTrue(text.contains("CraftChunk.createEmptyChunkSnapshot(chunkX, chunkZ, world, snapshotData)"));
        Assert.assertFalse(text.contains("new EmptyChunkSnapshot("));
    }

    @Test
    public void emptyChunkSnapshotFactoryBehaviourOwnsTheSnapshotConstruction() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHUNK_EMPTY_SNAPSHOT_FACTORY_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftChunkEmptySnapshot"));
        Assert.assertTrue(text.contains("new CraftChunkEmptySnapshot("));
        Assert.assertFalse(text.contains("new EmptyChunkSnapshot("));
    }

    @Test
    public void craftChunkSnapshotSystemOwnsTheSnapshotCaptureAndCreationOrchestration() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHUNK_SNAPSHOT_SYSTEM_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftChunkSnapshotSystem"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("captureFromChunk("));
        Assert.assertTrue(text.contains("CraftChunkSnapshotCreationBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_SNAPSHOT_CREATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createSnapshot("));
        Assert.assertTrue(text.contains("craftChunk.getHandle()"));
        Assert.assertTrue(text.contains("craftChunk.getX()"));
        Assert.assertTrue(text.contains("craftChunk.getZ()"));
        Assert.assertTrue(text.contains("craftChunk.getWorld()"));
        Assert.assertFalse(text.contains("return getChunkSnapshot("));
        Assert.assertTrue(text.contains("ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData = CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR.captureFromChunk("));
        Assert.assertTrue(text.contains("return CRAFT_CHUNK_SNAPSHOT_CREATION_BEHAVIOUR.createSnapshot("));
    }
}
