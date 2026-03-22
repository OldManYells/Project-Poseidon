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
    public void craftBlockDelegatesFaceConversionAndStateFactoryToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_BLOCK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BlockFaceConversionBehaviour"));
        Assert.assertTrue(text.contains("BlockMaterialPropertyBehaviour"));
        Assert.assertTrue(text.contains("BlockPowerQueryBehaviour"));
        Assert.assertTrue(text.contains("BiomeConversionBehaviour"));
        Assert.assertTrue(text.contains("CraftBlockStateFactoryBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.resolveAdjacentFace("));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(notch)"));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.blockFaceToNotch(face)"));
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
        Assert.assertFalse(text.contains("return power > 0 ? power : (face == BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0;"));
        Assert.assertFalse(text.contains("return (getType() == Material.WATER) || (getType() == Material.STATIONARY_WATER) || (getType() == Material.LAVA) || (getType() == Material.STATIONARY_LAVA);"));
        Assert.assertFalse(text.contains("return PistonMoveReaction.getById(net.minecraft.server.Block.byId[this.getTypeId()].material.j());"));
    }
}
