package com.legacyminecraft.poseidon.item;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.painting.PaintingPlaceEvent;

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
            org.bukkit.block.Block blockClicked = world.getWorld().getBlockAt(x, y, z);
            BlockFace blockFace = resolveBlockFace(face);
            PaintingPlaceEvent event = new PaintingPlaceEvent((org.bukkit.entity.Painting) painting.getBukkitEntity(), who, blockClicked, blockFace);
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
