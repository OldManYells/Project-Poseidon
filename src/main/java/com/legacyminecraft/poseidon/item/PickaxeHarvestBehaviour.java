package com.legacyminecraft.poseidon.item;


public final class PickaxeHarvestBehaviour {
    private static final PickaxeHarvestBehaviour INSTANCE = new PickaxeHarvestBehaviour();

    private PickaxeHarvestBehaviour() {
    }

    public static PickaxeHarvestBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canHarvest(Object block, Object material) {
        int tier = readTier(material);
        int blockId = readBlockId(block);

        if (blockId == Block.OBSIDIAN.id) {
            return tier == 3;
        }
        if (blockId == Block.DIAMOND_BLOCK.id || blockId == Block.DIAMOND_ORE.id) {
            return tier >= 2;
        }
        if (blockId == Block.GOLD_BLOCK.id || blockId == Block.GOLD_ORE.id) {
            return tier >= 2;
        }
        if (blockId == Block.IRON_BLOCK.id || blockId == Block.IRON_ORE.id) {
            return tier >= 1;
        }
        if (blockId == Block.LAPIS_BLOCK.id || blockId == Block.LAPIS_ORE.id) {
            return tier >= 1;
        }
        if (blockId == Block.REDSTONE_ORE.id || blockId == Block.GLOWING_REDSTONE_ORE.id) {
            return tier >= 2;
        }
        return isStoneOrOreMaterial(block);
    }

    private int readTier(Object material) {
        if (material == null) {
            return 0;
        }
        try {
            Object value = material.getClass().getMethod("d").invoke(material);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private int readBlockId(Object block) {
        if (block == null) {
            return -1;
        }
        try {
            return block.getClass().getField("id").getInt(block);
        } catch (ReflectiveOperationException ignored) {
            return -1;
        }
    }

    private boolean isStoneOrOreMaterial(Object block) {
        if (block == null) {
            return false;
        }
        try {
            Object material = block.getClass().getField("material").get(block);
            if (material == null) {
                return false;
            }
            String name = String.valueOf(material);
            return "STONE".equals(name) || "ORE".equals(name);
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }
}
