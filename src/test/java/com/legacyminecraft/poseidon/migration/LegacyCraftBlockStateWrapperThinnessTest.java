package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftBlockStateWrapperThinnessTest {
    private static final Path CRAFT_BLOCK_STATE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftBlockState.java");
    private static final Path BLOCK_STATE_SNAPSHOT_CAPTURE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/BlockStateSnapshotCaptureBehaviour.java");

    @Test
    public void craftBlockStateDelegatesDataValidationCreationTypeAndUpdatePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_BLOCK_STATE_PATH), StandardCharsets.UTF_8);
        String snapshotCaptureText =
                new String(Files.readAllBytes(BLOCK_STATE_SNAPSHOT_CAPTURE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BlockStateSnapshotBridgeBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_SNAPSHOT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getBlockState(world, x, y, z)"));
        Assert.assertTrue(text.contains("BlockStateIdentityAccessBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getWorld(world)"));
        Assert.assertTrue(text.contains("getX(x)"));
        Assert.assertTrue(text.contains("getY(y)"));
        Assert.assertTrue(text.contains("getZ(z)"));
        Assert.assertTrue(text.contains("getChunk(chunk)"));
        Assert.assertTrue(text.contains("BlockStateSnapshotInitializationBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_SNAPSHOT_INITIALIZATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("initialize(block)"));
        Assert.assertTrue(text.contains("this.data = snapshotState.getData();"));
        Assert.assertTrue(text.contains("BlockStateBlockLookupBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_BLOCK_LOOKUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getBlock(world, x, y, z)"));
        Assert.assertTrue(text.contains("BlockStateCoordinateProjectionBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_COORDINATE_PROJECTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getLocation(world, x, y, z)"));
        Assert.assertTrue(text.contains("BlockStateDataBehaviour"));
        Assert.assertTrue(text.contains("BlockStateRawDataBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_RAW_DATA_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getRawData(data)"));
        Assert.assertTrue(text.contains("setRawData(type, data, BLOCK_STATE_DATA_BEHAVIOUR)"));
        Assert.assertTrue(text.contains("BlockStateTypeMutationBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_TYPE_MUTATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("BLOCK_STATE_TYPE_MUTATION_BEHAVIOUR.setType(type, BLOCK_STATE_DATA_BEHAVIOUR)"));
        Assert.assertTrue(text.contains("setTypeId(type, BLOCK_STATE_DATA_BEHAVIOUR)"));
        Assert.assertTrue(text.contains("BlockStateUpdateBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_DATA_BEHAVIOUR.validateData(getType(), data)"));
        Assert.assertTrue(text.contains("BLOCK_STATE_UPDATE_BEHAVIOUR.applyUpdate(getBlock(), this.getType(), this.getTypeId(), getRawData(), force)"));
        Assert.assertFalse(text.contains("if ((mat == null) || (mat.getData() == null))"));
        Assert.assertFalse(text.contains("return new CraftBlockState(world.getWorld().getBlockAt(x, y, z));"));
        Assert.assertFalse(text.contains("return world;"));
        Assert.assertFalse(text.contains("return x;"));
        Assert.assertFalse(text.contains("return y;"));
        Assert.assertFalse(text.contains("return z;"));
        Assert.assertFalse(text.contains("return chunk;"));
        Assert.assertFalse(text.contains("return world.getBlockAt(x, y, z);"));
        Assert.assertFalse(text.contains("return new Location(world, x, y, z);"));
        Assert.assertFalse(text.contains("this.world = (CraftWorld) block.getWorld();"));
        Assert.assertFalse(text.contains("this.x = block.getX();"));
        Assert.assertFalse(text.contains("createData(block.getData());"));
        Assert.assertFalse(text.contains("BLOCK_STATE_SNAPSHOT_CAPTURE_BEHAVIOUR"));
        Assert.assertFalse(text.contains("createData(snapshotData.getData())"));
        Assert.assertFalse(text.contains("this.data = data;"));
        Assert.assertFalse(text.contains("this.type = type;"));
        Assert.assertFalse(text.contains("createData((byte) 0);"));
        Assert.assertFalse(text.contains("setTypeId(type.getId());"));
        Assert.assertFalse(text.contains("return data.getData();"));
        Assert.assertFalse(text.contains("createData(data);"));
        Assert.assertFalse(text.contains("synchronized (block)"));
        Assert.assertFalse(text.contains("this.data = mat.getNewData(data);"));
        Assert.assertTrue(snapshotCaptureText.contains("ChunkWrapperProjectionBridgeBehaviour"));
        Assert.assertTrue(snapshotCaptureText.contains("CHUNK_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftChunk(block.getChunk())"));
        Assert.assertFalse(snapshotCaptureText.contains("(CraftChunk) block.getChunk()"));
    }
}
