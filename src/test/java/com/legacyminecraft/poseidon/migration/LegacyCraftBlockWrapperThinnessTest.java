package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftBlockWrapperThinnessTest {
    private static final Path CRAFT_BLOCK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftBlock.java");

    @Test
    public void craftBlockDelegatesIdentityFaceConversionAndStateFactoryToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_BLOCK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BlockFaceConversionBehaviour"));
        Assert.assertTrue(text.contains("BlockIdentityAccessBehaviour"));
        Assert.assertTrue(text.contains("BlockClimateLookupBehaviour"));
        Assert.assertTrue(text.contains("BlockCoordinateProjectionBehaviour"));
        Assert.assertTrue(text.contains("BlockRelativeLookupBehaviour"));
        Assert.assertTrue(text.contains("BlockTypeLookupBehaviour"));
        Assert.assertTrue(text.contains("BlockMaterialPropertyBehaviour"));
        Assert.assertTrue(text.contains("BlockPowerQueryBehaviour"));
        Assert.assertTrue(text.contains("BiomeConversionBehaviour"));
        Assert.assertTrue(text.contains("CraftBlockIdentityBehaviour"));
        Assert.assertTrue(text.contains("CraftBlockStateFactoryBehaviour"));
        Assert.assertTrue(text.contains("CraftBlockWriteBehaviour"));
        Assert.assertTrue(text.contains("CraftBlockTypeMutationBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_IDENTITY_BEHAVIOUR.toString(chunk, x, y, z)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_IDENTITY_BEHAVIOUR.isSameInstance(this, o)"));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.resolveAdjacentFace("));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(notch)"));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.blockFaceToNotch(face)"));
        Assert.assertTrue(text.contains("BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getWorld(chunk)"));
        Assert.assertTrue(text.contains("BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getX(x)"));
        Assert.assertTrue(text.contains("BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getY(y)"));
        Assert.assertTrue(text.contains("BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getZ(z)"));
        Assert.assertTrue(text.contains("BLOCK_IDENTITY_ACCESS_BEHAVIOUR.getChunk(chunk)"));
        Assert.assertTrue(text.contains("BLOCK_CLIMATE_LOOKUP_BEHAVIOUR.getBiome(chunk.getHandle().world, x, z, BIOME_CONVERSION_BEHAVIOUR)"));
        Assert.assertTrue(text.contains("BLOCK_CLIMATE_LOOKUP_BEHAVIOUR.getTemperature(getWorld(), x, z)"));
        Assert.assertTrue(text.contains("BLOCK_CLIMATE_LOOKUP_BEHAVIOUR.getHumidity(getWorld(), x, z)"));
        Assert.assertTrue(text.contains("BLOCK_COORDINATE_PROJECTION_BEHAVIOUR.createLocation(getWorld(), x, y, z)"));
        Assert.assertTrue(text.contains("BLOCK_COORDINATE_PROJECTION_BEHAVIOUR.createVector(x, y, z)"));
        Assert.assertTrue(text.contains("BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getFace(getWorld(), getX(), getY(), getZ(), face)"));
        Assert.assertTrue(text.contains("BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getFace(getWorld(), getX(), getY(), getZ(), face, distance)"));
        Assert.assertTrue(text.contains("BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getRelative(getWorld(), getX(), getY(), getZ(), modX, modY, modZ)"));
        Assert.assertTrue(text.contains("BLOCK_RELATIVE_LOOKUP_BEHAVIOUR.getRelative(getWorld(), getX(), getY(), getZ(), face, distance)"));
        Assert.assertTrue(text.contains("BLOCK_TYPE_LOOKUP_BEHAVIOUR.getType(chunk.getHandle(), this.x, this.y, this.z)"));
        Assert.assertTrue(text.contains("BLOCK_TYPE_LOOKUP_BEHAVIOUR.getTypeId(chunk.getHandle(), this.x, this.y, this.z)"));
        Assert.assertTrue(text.contains("BLOCK_TYPE_LOOKUP_BEHAVIOUR.getData(chunk.getHandle(), this.x, this.y, this.z)"));
        Assert.assertTrue(text.contains("BLOCK_TYPE_LOOKUP_BEHAVIOUR.getLightLevel(chunk.getHandle().world, this.x, this.y, this.z)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_WRITE_BEHAVIOUR.setData(chunk.getHandle().world, x, y, z, data, true)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_WRITE_BEHAVIOUR.setData(chunk.getHandle().world, x, y, z, data, applyPhysics)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_WRITE_BEHAVIOUR.setTypeId(chunk.getHandle().world, x, y, z, type, true)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_WRITE_BEHAVIOUR.setTypeId(chunk.getHandle().world, x, y, z, type, applyPhysics)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_WRITE_BEHAVIOUR.setTypeIdAndData(chunk.getHandle().world, x, y, z, type, data, applyPhysics)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_TYPE_MUTATION_BEHAVIOUR.setType(this, type)"));
        Assert.assertTrue(text.contains("BLOCK_POWER_QUERY_BEHAVIOUR.getBlockPower(chunk.getHandle().world, x, y, z, face)"));
        Assert.assertTrue(text.contains("BLOCK_MATERIAL_PROPERTY_BEHAVIOUR.isLiquid(getType())"));
        Assert.assertTrue(text.contains("BLOCK_MATERIAL_PROPERTY_BEHAVIOUR.getPistonMoveReaction(this.getTypeId())"));
        Assert.assertTrue(text.contains("BIOME_CONVERSION_BEHAVIOUR.biomeBaseToBiome(base)"));
        Assert.assertTrue(text.contains("CRAFT_BLOCK_STATE_FACTORY_BEHAVIOUR.createState(this, getType())"));
        Assert.assertFalse(text.contains("for (BlockFace face : values)"));
        Assert.assertFalse(text.contains("return new CraftSign(this);"));
        Assert.assertFalse(text.contains("switch(face) {"));
        Assert.assertFalse(text.contains("if (base == BiomeBase.RAINFOREST)"));
        Assert.assertFalse(text.contains("BlockRedstoneWire wire = (BlockRedstoneWire) net.minecraft.server.Block.REDSTONE_WIRE;"));
        Assert.assertFalse(text.contains("return new Location(getWorld(), x, y, z);"));
        Assert.assertFalse(text.contains("return new BlockVector(x, y, z);"));
        Assert.assertFalse(text.contains("return chunk.getWorld();"));
        Assert.assertFalse(text.contains("return x;"));
        Assert.assertFalse(text.contains("return y;"));
        Assert.assertFalse(text.contains("return z;"));
        Assert.assertFalse(text.contains("return chunk;"));
        Assert.assertFalse(text.contains("return getRelative(face, 1);"));
        Assert.assertFalse(text.contains("return getRelative(face, distance);"));
        Assert.assertFalse(text.contains("return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);"));
        Assert.assertFalse(text.contains("return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);"));
        Assert.assertFalse(text.contains("return biomeBaseToBiome(chunk.getHandle().world.getWorldChunkManager().getBiome(x, z));"));
        Assert.assertFalse(text.contains("return getWorld().getTemperature(x, z);"));
        Assert.assertFalse(text.contains("return getWorld().getHumidity(x, z);"));
        Assert.assertFalse(text.contains("return Material.getMaterial(getTypeId());"));
        Assert.assertFalse(text.contains("return \"CraftBlock{\" + \"chunk=\" + chunk + \"x=\" + x + \"y=\" + y + \"z=\" + z + '}';"));
        Assert.assertFalse(text.contains("return this == o;"));
        Assert.assertFalse(text.contains("return chunk.getHandle().getTypeId(this.x & 0xF, this.y & 0x7F, this.z & 0xF);"));
        Assert.assertFalse(text.contains("return (byte) chunk.getHandle().getData(this.x & 0xF, this.y & 0x7F, this.z & 0xF);"));
        Assert.assertFalse(text.contains("return (byte) chunk.getHandle().world.getLightLevel(this.x, this.y, this.z);"));
        Assert.assertFalse(text.contains("chunk.getHandle().world.setRawData(x, y, z, data);"));
        Assert.assertFalse(text.contains("chunk.getHandle().world.setRawTypeId(x, y, z, type);"));
        Assert.assertFalse(text.contains("chunk.getHandle().world.setRawTypeIdAndData(x, y, z, type, data);"));
        Assert.assertFalse(text.contains("setTypeId(type.getId());"));
        Assert.assertFalse(text.contains("return power > 0 ? power : (face == BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0;"));
        Assert.assertFalse(text.contains("return (getType() == Material.WATER) || (getType() == Material.STATIONARY_WATER) || (getType() == Material.LAVA) || (getType() == Material.STATIONARY_LAVA);"));
        Assert.assertFalse(text.contains("return PistonMoveReaction.getById(net.minecraft.server.Block.byId[this.getTypeId()].material.j());"));
    }
}
