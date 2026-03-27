package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.AxisAlignedBB;
import com.legacyminecraft.poseidon.world.Entity;
import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical block scaffold used by migrated block/item/world behaviours.
 */
public class Block {
    public static final Block[] byId = new Block[4096];

    public static final Block FIRE = register(new Block(51, Material.AIR));
    public static final Block DIRT = register(new Block(3, Material.AIR));
    public static final Block STONE = register(new Block(1, Material.AIR));
    public static final Block PISTON_MOVING = register(new Block(36, Material.AIR));
    public static final Block PISTON = register(new Block(33, Material.AIR));
    public static final Block PISTON_STICKY = register(new Block(29, Material.AIR));
    public static final Block PISTON_EXTENSION = register(new Block(34, Material.AIR));
    public static final Block OBSIDIAN = register(new Block(49, Material.AIR));
    public static final Block FURNACE = register(new Block(61, Material.AIR));
    public static final Block BURNING_FURNACE = register(new Block(62, Material.AIR));
    public static final Block LEAVES = register(new Block(18, Material.AIR));
    public static final Block WEB = register(new Block(30, Material.AIR));
    public static final Block WOOL = register(new Block(35, Material.AIR));
    public static final Block REDSTONE_WIRE = register(new Block(55, Material.AIR));
    public static final Block DISPENSER = register(new Block(23, Material.AIR));
    public static final Block RAILS = register(new Block(66, Material.AIR));
    public static final Block GOLDEN_RAIL = register(new Block(27, Material.AIR));
    public static final Block DETECTOR_RAIL = register(new Block(28, Material.AIR));
    public static final Block FENCE = register(new Block(85, Material.AIR));

    public final int id;
    public final Material material;
    public float frictionFactor = 0.6F;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX = 1.0D;
    public double maxY = 1.0D;
    public double maxZ = 1.0D;

    public Block(int id, Material material) {
        this.id = id;
        this.material = material;
    }

    private static Block register(Block block) {
        if (block.id >= 0 && block.id < byId.length) {
            byId[block.id] = block;
        }
        return block;
    }

    public float a(Entity source) {
        return 0.0F;
    }

    public boolean b() {
        return true;
    }

    public AxisAlignedBB e(World world, int x, int y, int z) {
        return AxisAlignedBB.a(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
    }

    public void dropNaturally(World world, int x, int y, int z, int data, float yield) {
    }

    public void d(World world, int x, int y, int z) {
    }

    public void g(World world, int x, int y, int z, int data) {
    }

    public void doPhysics(World world, int x, int y, int z, int sourceTypeId) {
    }

    public AxisAlignedBB a(World world, int x, int y, int z, int movedBlockId, float progress, int facing) {
        Block movedBlock = movedBlockId >= 0 && movedBlockId < byId.length ? byId[movedBlockId] : null;
        if (movedBlock == null) {
            return null;
        }
        return movedBlock.e(world, x, y, z);
    }
}
