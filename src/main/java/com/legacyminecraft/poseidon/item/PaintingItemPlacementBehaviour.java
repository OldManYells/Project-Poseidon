package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.compat.bukkit.BlockFace;

public final class PaintingItemPlacementBehaviour {
    private static final PaintingItemPlacementBehaviour INSTANCE = new PaintingItemPlacementBehaviour();

    private PaintingItemPlacementBehaviour() {
    }

    public static PaintingItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean place(ItemStack itemstack, EntityHuman entityhuman, World world, int x, int y, int z, int face) {
        if (face == 0 || face == 1) {
            return false;
        }

        byte direction = resolveDirection(face);
        EntityPainting painting = new EntityPainting(world, x, y, z, direction);
        if (!painting.h()) {
            return true;
        }

        if (!world.isStatic) {
            Player who = entityhuman == null ? null : (Player) entityhuman.getBukkitEntity();
            com.legacyminecraft.compat.bukkit.block.Block blockClicked = world.getWorld().getBlockAt(x, y, z);
            BlockFace blockFace = resolveBlockFace(face);
            PaintingPlaceEvent event = new PaintingPlaceEvent((com.legacyminecraft.compat.bukkit.entity.Painting) painting.getBukkitEntity(), who, blockClicked, blockFace);
            world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return false;
            }
            world.addEntity(painting);
        }

        --itemstack.count;
        return true;
    }

    public byte resolveDirection(int face) {
        if (face == 4) {
            return 1;
        }
        if (face == 3) {
            return 2;
        }
        if (face == 5) {
            return 3;
        }
        return 0;
    }

    public BlockFace resolveBlockFace(int face) {
        switch (face) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.EAST;
            case 3:
                return BlockFace.WEST;
            case 4:
                return BlockFace.NORTH;
            case 5:
                return BlockFace.SOUTH;
            default:
                return BlockFace.SELF;
        }
    }
}
