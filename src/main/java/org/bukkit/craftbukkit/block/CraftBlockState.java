
package org.bukkit.craftbukkit.block;

import com.legacyminecraft.compat.bukkit.BlockStateSnapshotBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateBlockLookupBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateCoordinateProjectionBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateDataBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateIdentityAccessBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateRawDataBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateSnapshotInitializationBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateTypeMutationBehaviour;
import com.legacyminecraft.compat.bukkit.BlockStateUpdateBehaviour;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.material.MaterialData;

public class CraftBlockState implements BlockState {
    private static final BlockStateSnapshotBridgeBehaviour BLOCK_STATE_SNAPSHOT_BRIDGE_BEHAVIOUR =
            BlockStateSnapshotBridgeBehaviour.getInstance();
    private static final BlockStateIdentityAccessBehaviour BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR =
            BlockStateIdentityAccessBehaviour.getInstance();
    private static final BlockStateSnapshotInitializationBehaviour BLOCK_STATE_SNAPSHOT_INITIALIZATION_BEHAVIOUR =
            BlockStateSnapshotInitializationBehaviour.getInstance();
    private static final BlockStateBlockLookupBehaviour BLOCK_STATE_BLOCK_LOOKUP_BEHAVIOUR =
            BlockStateBlockLookupBehaviour.getInstance();
    private static final BlockStateCoordinateProjectionBehaviour BLOCK_STATE_COORDINATE_PROJECTION_BEHAVIOUR =
            BlockStateCoordinateProjectionBehaviour.getInstance();
    private static final BlockStateDataBehaviour BLOCK_STATE_DATA_BEHAVIOUR =
            BlockStateDataBehaviour.getInstance();
    private static final BlockStateRawDataBehaviour BLOCK_STATE_RAW_DATA_BEHAVIOUR =
            BlockStateRawDataBehaviour.getInstance();
    private static final BlockStateTypeMutationBehaviour BLOCK_STATE_TYPE_MUTATION_BEHAVIOUR =
            BlockStateTypeMutationBehaviour.getInstance();
    private static final BlockStateUpdateBehaviour BLOCK_STATE_UPDATE_BEHAVIOUR =
            BlockStateUpdateBehaviour.getInstance();
    private final CraftWorld world;
    private final CraftChunk chunk;
    private final int x;
    private final int y;
    private final int z;
    protected int type;
    protected MaterialData data;
    protected byte light;

    public CraftBlockState(final Block block) {
        BlockStateSnapshotInitializationBehaviour.SnapshotState snapshotState =
                BLOCK_STATE_SNAPSHOT_INITIALIZATION_BEHAVIOUR.initialize(block);
        this.world = snapshotState.getWorld();
        this.chunk = snapshotState.getChunk();
        this.x = snapshotState.getX();
        this.y = snapshotState.getY();
        this.z = snapshotState.getZ();
        this.type = snapshotState.getTypeId();
        this.light = snapshotState.getLightLevel();
        this.data = snapshotState.getData();
    }

    public static CraftBlockState getBlockState(net.minecraft.server.World world, int x, int y, int z) {
        return BLOCK_STATE_SNAPSHOT_BRIDGE_BEHAVIOUR.getBlockState(world, x, y, z);
    }

    /**
     * Gets the world which contains this Block
     *
     * @return World containing this block
     */
    public World getWorld() {
        return BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR.getWorld(world);
    }

    /**
     * Gets the x-coordinate of this block
     *
     * @return x-coordinate
     */
    public int getX() {
        return BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR.getX(x);
    }

    /**
     * Gets the y-coordinate of this block
     *
     * @return y-coordinate
     */
    public int getY() {
        return BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR.getY(y);
    }

    /**
     * Gets the z-coordinate of this block
     *
     * @return z-coordinate
     */
    public int getZ() {
        return BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR.getZ(z);
    }

    /**
     * Gets the chunk which contains this block
     *
     * @return Containing Chunk
     */
    public Chunk getChunk() {
        return BLOCK_STATE_IDENTITY_ACCESS_BEHAVIOUR.getChunk(chunk);
    }

    /**
     * Sets the metadata for this block
     *
     * @param data New block specific metadata
     */
    public void setData(final MaterialData data) {
        this.data = BLOCK_STATE_DATA_BEHAVIOUR.validateData(getType(), data);
    }

    /**
     * Gets the metadata for this block
     *
     * @return block specific metadata
     */
    public MaterialData getData() {
        return data;
    }

    /**
     * Sets the type of this block
     *
     * @param type Material to change this block to
     */
    public void setType(final Material type) {
        BlockStateTypeMutationBehaviour.MutationResult mutationResult =
                BLOCK_STATE_TYPE_MUTATION_BEHAVIOUR.setType(type, BLOCK_STATE_DATA_BEHAVIOUR);
        this.type = mutationResult.getTypeId();
        this.data = mutationResult.getData();
    }

    /**
     * Sets the type-id of this block
     *
     * @param type Type-Id to change this block to
     */
    public boolean setTypeId(final int type) {
        BlockStateTypeMutationBehaviour.MutationResult mutationResult =
                BLOCK_STATE_TYPE_MUTATION_BEHAVIOUR.setTypeId(type, BLOCK_STATE_DATA_BEHAVIOUR);
        this.type = mutationResult.getTypeId();
        this.data = mutationResult.getData();
        return true;
    }

    /**
     * Gets the type of this block
     *
     * @return block type
     */
    public Material getType() {
        return Material.getMaterial(getTypeId());
    }

    /**
     * Gets the type-id of this block
     *
     * @return block type-id
     */
    public int getTypeId() {
        return type;
    }

    /**
     * Gets the light level between 0-15
     *
     * @return light level
     */
    public byte getLightLevel() {
        return light;
    }

    public Block getBlock() {
        return BLOCK_STATE_BLOCK_LOOKUP_BEHAVIOUR.getBlock(world, x, y, z);
    }

    public boolean update() {
        return update(false);
    }

    public boolean update(boolean force) {
        return BLOCK_STATE_UPDATE_BEHAVIOUR.applyUpdate(getBlock(), this.getType(), this.getTypeId(), getRawData(), force);
    }

    public byte getRawData() {
        return BLOCK_STATE_RAW_DATA_BEHAVIOUR.getRawData(data);
    }

    public Location getLocation() {
        return BLOCK_STATE_COORDINATE_PROJECTION_BEHAVIOUR.getLocation(world, x, y, z);
    }

    public void setData(byte data) {
        this.data = BLOCK_STATE_RAW_DATA_BEHAVIOUR.setRawData(type, data, BLOCK_STATE_DATA_BEHAVIOUR);
    }
}
