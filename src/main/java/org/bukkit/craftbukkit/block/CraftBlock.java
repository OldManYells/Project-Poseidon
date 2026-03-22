package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.compat.bukkit.BlockFaceConversionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BlockMaterialPropertyBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BiomeConversionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BlockPowerQueryBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftBlockStateFactoryBehaviour;
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
    private static final BiomeConversionBehaviour BIOME_CONVERSION_BEHAVIOUR =
            BiomeConversionBehaviour.getInstance();
    private static final BlockPowerQueryBehaviour BLOCK_POWER_QUERY_BEHAVIOUR =
            BlockPowerQueryBehaviour.getInstance();
    private static final BlockMaterialPropertyBehaviour BLOCK_MATERIAL_PROPERTY_BEHAVIOUR =
            BlockMaterialPropertyBehaviour.getInstance();
    private static final CraftBlockStateFactoryBehaviour CRAFT_BLOCK_STATE_FACTORY_BEHAVIOUR =
            CraftBlockStateFactoryBehaviour.getInstance();
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
        return chunk.getWorld();
    }

    public Location getLocation() {
        return new Location(getWorld(), x, y, z);
    }

    public BlockVector getVector() {
        return new BlockVector(x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setData(final byte data) {
        chunk.getHandle().world.setData(x, y, z, data);
    }

    public void setData(final byte data, boolean applyPhysics) {
        if (applyPhysics) {
            chunk.getHandle().world.setData(x, y, z, data);
        } else {
            chunk.getHandle().world.setRawData(x, y, z, data);
        }
    }

    public byte getData() {
        return (byte) chunk.getHandle().getData(this.x & 0xF, this.y & 0x7F, this.z & 0xF);
    }

    public void setType(final Material type) {
        setTypeId(type.getId());
    }

    public boolean setTypeId(final int type) {
        return chunk.getHandle().world.setTypeId(x, y, z, type);
    }

    public boolean setTypeId(final int type, final boolean applyPhysics) {
        if (applyPhysics) {
            return setTypeId(type);
        } else {
            return chunk.getHandle().world.setRawTypeId(x, y, z, type);
        }
    }

    public boolean setTypeIdAndData(final int type, final byte data, final boolean applyPhysics) {
        if (applyPhysics) {
            return chunk.getHandle().world.setTypeIdAndData(x, y, z, type, data);
        } else {
            boolean success = chunk.getHandle().world.setRawTypeIdAndData(x, y, z, type, data);
            if (success) {
                chunk.getHandle().world.notify(x, y, z);
            }
            return success;
        }
    }

    public Material getType() {
        return Material.getMaterial(getTypeId());
    }

    public int getTypeId() {
        return chunk.getHandle().getTypeId(this.x & 0xF, this.y & 0x7F, this.z & 0xF);
    }

    public byte getLightLevel() {
        return (byte) chunk.getHandle().world.getLightLevel(this.x, this.y, this.z);
    }

    public Block getFace(final BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getFace(final BlockFace face, final int distance) {
        return getRelative(face, distance);
    }

    public Block getRelative(final int modX, final int modY, final int modZ) {
        return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
    }

    public Block getRelative(BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getRelative(BlockFace face, int distance) {
        return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }

    public BlockFace getFace(final Block block) {
        return BLOCK_FACE_CONVERSION_BEHAVIOUR.resolveAdjacentFace(this.getX(), this.getY(), this.getZ(), block);
    }

    @Override
    public String toString() {
        return "CraftBlock{" + "chunk=" + chunk + "x=" + x + "y=" + y + "z=" + z + '}';
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
        return biomeBaseToBiome(chunk.getHandle().world.getWorldChunkManager().getBiome(x, z));
    }

    public static final Biome biomeBaseToBiome(BiomeBase base) {
        return BIOME_CONVERSION_BEHAVIOUR.biomeBaseToBiome(base);
    }

    public double getTemperature() {
        return getWorld().getTemperature(x, z);
    }

    public double getHumidity() {
        return getWorld().getHumidity(x, z);
    }

    public boolean isBlockPowered() {
        return BLOCK_POWER_QUERY_BEHAVIOUR.isBlockPowered(chunk.getHandle().world, x, y, z);
    }

    public boolean isBlockIndirectlyPowered() {
        return BLOCK_POWER_QUERY_BEHAVIOUR.isBlockIndirectlyPowered(chunk.getHandle().world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
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
