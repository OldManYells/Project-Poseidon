package org.bukkit.craftbukkit.block;

import com.legacyminecraft.compat.bukkit.BlockFaceConversionBehaviour;
import com.legacyminecraft.compat.bukkit.BlockClimateLookupBehaviour;
import com.legacyminecraft.compat.bukkit.BlockCoordinateProjectionBehaviour;
import com.legacyminecraft.compat.bukkit.CraftBlockIdentityBehaviour;
import com.legacyminecraft.compat.bukkit.BlockIdentityAccessBehaviour;
import com.legacyminecraft.compat.bukkit.BlockMaterialPropertyBehaviour;
import com.legacyminecraft.compat.bukkit.BiomeConversionBehaviour;
import com.legacyminecraft.compat.bukkit.BlockPowerQueryBehaviour;
import com.legacyminecraft.compat.bukkit.CraftBlockStateFactoryBehaviour;
import com.legacyminecraft.compat.bukkit.CraftBlockTypeMutationBehaviour;
import com.legacyminecraft.compat.bukkit.CraftBlockWriteBehaviour;
import com.legacyminecraft.compat.bukkit.BlockRelativeLookupBehaviour;
import com.legacyminecraft.compat.bukkit.BlockTypeLookupBehaviour;
import net.minecraft.server.BiomeBase;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.util.BlockVector;

public class CraftBlock implements Block {
    private static final BlockFaceConversionBehaviour BLOCK_FACE_CONVERSION_BEHAVIOUR =
            BlockFaceConversionBehaviour.getInstance();
    private static final BlockIdentityAccessBehaviour BLOCK_IDENTITY_ACCESS_BEHAVIOUR =
            BlockIdentityAccessBehaviour.getInstance();
    private static final BlockClimateLookupBehaviour BLOCK_CLIMATE_LOOKUP_BEHAVIOUR =
            BlockClimateLookupBehaviour.getInstance();
    private static final BiomeConversionBehaviour BIOME_CONVERSION_BEHAVIOUR =
            BiomeConversionBehaviour.getInstance();
    private static final BlockPowerQueryBehaviour BLOCK_POWER_QUERY_BEHAVIOUR =
            BlockPowerQueryBehaviour.getInstance();
    private static final BlockMaterialPropertyBehaviour BLOCK_MATERIAL_PROPERTY_BEHAVIOUR =
            BlockMaterialPropertyBehaviour.getInstance();
    private static final BlockCoordinateProjectionBehaviour BLOCK_COORDINATE_PROJECTION_BEHAVIOUR =
            BlockCoordinateProjectionBehaviour.getInstance();
    private static final CraftBlockStateFactoryBehaviour CRAFT_BLOCK_STATE_FACTORY_BEHAVIOUR =
            CraftBlockStateFactoryBehaviour.getInstance();
    private static final CraftBlockWriteBehaviour CRAFT_BLOCK_WRITE_BEHAVIOUR =
            CraftBlockWriteBehaviour.getInstance();
    private static final CraftBlockTypeMutationBehaviour CRAFT_BLOCK_TYPE_MUTATION_BEHAVIOUR =
            CraftBlockTypeMutationBehaviour.getInstance();
    private static final CraftBlockIdentityBehaviour CRAFT_BLOCK_IDENTITY_BEHAVIOUR =
            CraftBlockIdentityBehaviour.getInstance();
    private static final BlockRelativeLookupBehaviour BLOCK_RELATIVE_LOOKUP_BEHAVIOUR =
            BlockRelativeLookupBehaviour.getInstance();
    private static final BlockTypeLookupBehaviour BLOCK_TYPE_LOOKUP_BEHAVIOUR =
            BlockTypeLookupBehaviour.getInstance();
    private final CraftChunk chunk;
    private final int x;
    private final int y;
    private final int z;

    public CraftBlock(CraftChunk chunk, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunk = chunk;
    }

    public World getWorld() {
        return BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getWorld(chunk);
    }

    public Location getLocation() {
        return BLOCK_COORDINATE_PROJECTION_BEHAVIOUR.createLocation(getWorld(), x, y, z);
    }

    public BlockVector getVector() {
        return BLOCK_COORDINATE_PROJECTION_BEHAVIOUR.createVector(x, y, z);
    }

    public int getX() {
        return BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getX(x);
    }

    public int getY() {
        return BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getY(y);
    }

    public int getZ() {
        return BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getZ(z);
    }

    public Chunk getChunk() {
        return BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getChunk(chunk);
    }

    public void setData(final byte data) {
        CRAFT_BLOCK_WRITE_BEHAVIOUR.setData(chunk.getHandle().world, x, y, z, data, true);
    }

    public void setData(final byte data, boolean applyPhysics) {
        CRAFT_BLOCK_WRITE_BEHAVIOUR.setData(chunk.getHandle().world, x, y, z, data, applyPhysics);
    }

    public byte getData() {
        return BLOCK_TYPE_LOOKUP_BEHAVIOUR.getData(chunk.getHandle(), this.x, this.y, this.z);
    }

    public void setType(final Material type) {
        CRAFT_BLOCK_TYPE_MUTATION_BEHAVIOUR.setType(this, type);
    }

    public boolean setTypeId(final int type) {
        return CRAFT_BLOCK_WRITE_BEHAVIOUR.setTypeId(chunk.getHandle().world, x, y, z, type, true);
    }

    public boolean setTypeId(final int type, final boolean applyPhysics) {
        return CRAFT_BLOCK_WRITE_BEHAVIOUR.setTypeId(chunk.getHandle().world, x, y, z, type, applyPhysics);
    }

    public boolean setTypeIdAndData(final int type, final byte data, final boolean applyPhysics) {
        return CRAFT_BLOCK_WRITE_BEHAVIOUR.setTypeIdAndData(chunk.getHandle().world, x, y, z, type, data, applyPhysics);
    }

    public Material getType() {
        return BLOCK_TYPE_LOOKUP_BEHAVIOUR.getType(chunk.getHandle(), this.x, this.y, this.z);
    }

    public int getTypeId() {
        return BLOCK_TYPE_LOOKUP_BEHAVIOUR.getTypeId(chunk.getHandle(), this.x, this.y, this.z);
    }

    public byte getLightLevel() {
        return BLOCK_TYPE_LOOKUP_BEHAVIOUR.getLightLevel(chunk.getHandle().world, this.x, this.y, this.z);
    }

    public Block getFace(final BlockFace face) {
        return BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getFace(getWorld(), getX(), getY(), getZ(), face);
    }

    public Block getFace(final BlockFace face, final int distance) {
        return BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getFace(getWorld(), getX(), getY(), getZ(), face, distance);
    }

    public Block getRelative(final int modX, final int modY, final int modZ) {
        return BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getRelative(getWorld(), getX(), getY(), getZ(), modX, modY, modZ);
    }

    public Block getRelative(BlockFace face) {
        return BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getRelative(getWorld(), getX(), getY(), getZ(), face, 1);
    }

    public Block getRelative(BlockFace face, int distance) {
        return BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getRelative(getWorld(), getX(), getY(), getZ(), face, distance);
    }

    public BlockFace getFace(final Block block) {
        return BLOCK_FACE_CONVERSION_BEHAVIOUR.resolveAdjacentFace(this.getX(), this.getY(), this.getZ(), block);
    }

    @Override
    public String toString() {
        return CRAFT_BLOCK_IDENTITY_BEHAVIOUR.toString(chunk, x, y, z);
    }

    /**
     * Notch uses a 0-5 to mean DOWN, UP, EAST, WEST, NORTH, SOUTH
     * in that order all over. This method is convenience to convert for us.
     *
     * @return BlockFace the BlockFace represented by this number
     */
    public static BlockFace notchToBlockFace(int notch) {
        return BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(notch);
    }

    public static int blockFaceToNotch(BlockFace face) {
        return BLOCK_FACE_CONVERSION_BEHAVIOUR.blockFaceToNotch(face);
    }

    public BlockState getState() {
        return CRAFT_BLOCK_STATE_FACTORY_BEHAVIOUR.createState(this, getType());
    }

    public Biome getBiome() {
        return BLOCK_CLIMATE_LOOKUP_BEHAVIOUR.getBiome(chunk.getHandle().world, x, z, BIOME_CONVERSION_BEHAVIOUR);
    }

    public static final Biome biomeBaseToBiome(BiomeBase base) {
        return BIOME_CONVERSION_BEHAVIOUR.biomeBaseToBiome(base);
    }

    public double getTemperature() {
        return BLOCK_CLIMATE_LOOKUP_BEHAVIOUR.getTemperature(getWorld(), x, z);
    }

    public double getHumidity() {
        return BLOCK_CLIMATE_LOOKUP_BEHAVIOUR.getHumidity(getWorld(), x, z);
    }

    public boolean isBlockPowered() {
        return BLOCK_POWER_QUERY_BEHAVIOUR.isBlockPowered(chunk.getHandle().world, x, y, z);
    }

    public boolean isBlockIndirectlyPowered() {
        return BLOCK_POWER_QUERY_BEHAVIOUR.isBlockIndirectlyPowered(chunk.getHandle().world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        return CRAFT_BLOCK_IDENTITY_BEHAVIOUR.isSameInstance(this, o);
    }

    public boolean isBlockFacePowered(BlockFace face) {
        return BLOCK_POWER_QUERY_BEHAVIOUR.isBlockFacePowered(
                chunk.getHandle().world,
                x,
                y,
                z,
                face,
                BLOCK_FACE_CONVERSION_BEHAVIOUR
        );
    }

    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        return BLOCK_POWER_QUERY_BEHAVIOUR.isBlockFaceIndirectlyPowered(
                chunk.getHandle().world,
                x,
                y,
                z,
                face,
                BLOCK_FACE_CONVERSION_BEHAVIOUR
        );
    }

    public int getBlockPower(BlockFace face) {
        return BLOCK_POWER_QUERY_BEHAVIOUR.getBlockPower(chunk.getHandle().world, x, y, z, face);
    }

    public int getBlockPower() {
        return getBlockPower(BlockFace.SELF);
    }

    public boolean isEmpty() {
        return BLOCK_MATERIAL_PROPERTY_BEHAVIOUR.isEmpty(getType());
    }

    public boolean isLiquid() {
        return BLOCK_MATERIAL_PROPERTY_BEHAVIOUR.isLiquid(getType());
    }

    public PistonMoveReaction getPistonMoveReaction() {
        return BLOCK_MATERIAL_PROPERTY_BEHAVIOUR.getPistonMoveReaction(this.getTypeId());
    }
}
