package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBlock block-state wrapper selection.
 */
public final class CraftBlockStateFactoryBehaviour {
    private static final CraftBlockStateFactoryBehaviour INSTANCE = new CraftBlockStateFactoryBehaviour();

    private CraftBlockStateFactoryBehaviour() {
    }

    public static CraftBlockStateFactoryBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.block.BlockState createState(org.bukkit.craftbukkit.block.CraftBlock block, org.bukkit.Material material) {
        switch (material) {
            case SIGN:
            case SIGN_POST:
            case WALL_SIGN:
                return new org.bukkit.craftbukkit.block.CraftSign(block);
            case CHEST:
                return new org.bukkit.craftbukkit.block.CraftChest(block);
            case BURNING_FURNACE:
            case FURNACE:
                return new org.bukkit.craftbukkit.block.CraftFurnace(block);
            case DISPENSER:
                return new org.bukkit.craftbukkit.block.CraftDispenser(block);
            case MOB_SPAWNER:
                return new org.bukkit.craftbukkit.block.CraftCreatureSpawner(block);
            case NOTE_BLOCK:
                return new org.bukkit.craftbukkit.block.CraftNoteBlock(block);
            default:
                return new org.bukkit.craftbukkit.block.CraftBlockState(block);
        }
    }
}
