package com.legacyminecraft.poseidon.item;

/**
 * Item-local block scaffold.
 */
public class Block extends com.legacyminecraft.poseidon.block.Block {
    public static final Block[] byId = new Block[4096];

    public static final Block FIRE = register(new Block(51, Material.AIR));
    public static final Block WOOD = register(new Block(5, Material.SOLID));
    public static final Block BOOKSHELF = register(new Block(47, Material.SOLID));
    public static final Block LOG = register(new Block(17, Material.SOLID));
    public static final Block CHEST = register(new Block(54, Material.SOLID));
    public static final Block SNOW = register(new Block(78, Material.SOLID));
    public static final Block STEP = register(new Block(44, Material.STONE));
    public static final Block DOUBLE_STEP = register(new Block(43, Material.STONE));
    public static final Block ICE = register(new Block(79, Material.SOLID));
    public static final Block OBSIDIAN = register(new Block(49, Material.STONE));
    public static final Block DIAMOND_BLOCK = register(new Block(57, Material.ORE));
    public static final Block DIAMOND_ORE = register(new Block(56, Material.ORE));
    public static final Block GOLD_BLOCK = register(new Block(41, Material.ORE));
    public static final Block GOLD_ORE = register(new Block(14, Material.ORE));
    public static final Block IRON_BLOCK = register(new Block(42, Material.ORE));
    public static final Block IRON_ORE = register(new Block(15, Material.ORE));
    public static final Block LAPIS_BLOCK = register(new Block(22, Material.ORE));
    public static final Block LAPIS_ORE = register(new Block(21, Material.ORE));
    public static final Block REDSTONE_ORE = register(new Block(73, Material.ORE));
    public static final Block GLOWING_REDSTONE_ORE = register(new Block(74, Material.ORE));
    public static final Block WEB = register(new Block(30, Material.SOLID));
    public static final Block GRASS = register(new Block(2, Material.SOLID));
    public static final Block DIRT = register(new Block(3, Material.SOLID));
    public static final Block CROPS = register(new BlockCrops(59, Material.SOLID));
    public static final Block LONG_GRASS = register(new Block(31, Material.SOLID));
    public static final Block YELLOW_FLOWER = register(new BlockFlower(37, Material.SOLID));
    public static final Block RED_ROSE = register(new BlockFlower(38, Material.SOLID));
    public static final Block PUMPKIN = register(new Block(86, Material.SOLID));
    public static final Block JUKEBOX = register(new BlockJukeBox(84, Material.SOLID));
    public static final Block SOIL = register(new Block(60, Material.SOLID));
    public static final Block WOOL = register(new BlockCloth(35, Material.SOLID));
    public static final Block SNOW_BLOCK = register(new Block(80, Material.SOLID));
    public static final Block SAPLING = register(new BlockSapling(6, Material.SOLID));
    public static final Block LEAVES = register(new Block(18, Material.SOLID));
    public static final Block SAND = register(new Block(12, Material.SOLID));
    public static final Block GRAVEL = register(new Block(13, Material.SOLID));
    public static final Block MINECART_TRACK = register(new BlockMinecartTrack(66, Material.SOLID));
    public static final Block REED = register(new Block(83, Material.SOLID));

    public final Material material;

    public Block(int id, Material material) {
        super(id, com.legacyminecraft.poseidon.block.Material.AIR);
        this.material = material;
    }

    private static Block register(Block block) {
        if (block.id >= 0 && block.id < byId.length) {
            byId[block.id] = block;
        }
        if (block.id >= 0 && block.id < com.legacyminecraft.poseidon.block.Block.byId.length) {
            com.legacyminecraft.poseidon.block.Block.byId[block.id] = block;
        }
        return block;
    }

    public String l() {
        return "tile." + id;
    }

    public boolean canPlace(World world, int x, int y, int z) {
        return true;
    }
}
