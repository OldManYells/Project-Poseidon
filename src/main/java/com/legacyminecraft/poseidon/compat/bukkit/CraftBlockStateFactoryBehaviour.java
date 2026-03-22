package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.block.CraftChest;
import org.bukkit.craftbukkit.block.CraftCreatureSpawner;
import org.bukkit.craftbukkit.block.CraftDispenser;
import org.bukkit.craftbukkit.block.CraftFurnace;
import org.bukkit.craftbukkit.block.CraftNoteBlock;
import org.bukkit.craftbukkit.block.CraftSign;

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

    public BlockState createState(CraftBlock block, Material material) {
        switch (material) {
            case SIGN:
            case SIGN_POST:
            case WALL_SIGN:
                return new CraftSign(block);
            case CHEST:
                return new CraftChest(block);
            case BURNING_FURNACE:
            case FURNACE:
                return new CraftFurnace(block);
            case DISPENSER:
                return new CraftDispenser(block);
            case MOB_SPAWNER:
                return new CraftCreatureSpawner(block);
            case NOTE_BLOCK:
                return new CraftNoteBlock(block);
            default:
                return new CraftBlockState(block);
        }
    }
}
