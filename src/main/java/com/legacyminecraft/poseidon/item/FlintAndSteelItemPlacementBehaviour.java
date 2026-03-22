package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.compat.bukkit.BlockPlaceEventBridgeBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public final class FlintAndSteelItemPlacementBehaviour {
    private static final FlintAndSteelItemPlacementBehaviour INSTANCE = new FlintAndSteelItemPlacementBehaviour();
    private static final BlockPlaceEventBridgeBehaviour BLOCK_PLACE_EVENT_BRIDGE = BlockPlaceEventBridgeBehaviour.getInstance();

    private FlintAndSteelItemPlacementBehaviour() {
    }

    public static FlintAndSteelItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean placeFire(ItemStack itemstack, EntityHuman entityhuman, World world, int x, int y, int z, int face, java.util.Random random) {
        int clickedX = x;
        int clickedY = y;
        int clickedZ = z;

        if (face == 0) {
            --y;
        }
        if (face == 1) {
            ++y;
        }
        if (face == 2) {
            --z;
        }
        if (face == 3) {
            ++z;
        }
        if (face == 4) {
            --x;
        }
        if (face == 5) {
            ++x;
        }

        int targetBlockId = world.getTypeId(x, y, z);
        if (targetBlockId == 0) {
            org.bukkit.block.Block blockClicked = world.getWorld().getBlockAt(x, y, z);
            Player thePlayer = (Player) entityhuman.getBukkitEntity();
            BlockIgniteEvent eventIgnite = new BlockIgniteEvent(blockClicked, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, thePlayer);
            world.getServer().getPluginManager().callEvent(eventIgnite);
            if (eventIgnite.isCancelled()) {
                itemstack.damage(1, entityhuman);
                return false;
            }

            BlockState blockState = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world, x, y, z);
            world.makeSound((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "fire.ignite", 1.0F, random.nextFloat() * 0.4F + 0.8F);
            world.setTypeId(x, y, z, Block.FIRE.id);

            BlockPlaceEvent placeEvent = BLOCK_PLACE_EVENT_BRIDGE.callBlockPlaceEvent(
                    world,
                    entityhuman,
                    blockState,
                    clickedX,
                    clickedY,
                    clickedZ,
                    Block.FIRE.id
            );
            if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
                placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte) 0, false);
                return false;
            }
        }

        itemstack.damage(1, entityhuman);
        return true;
    }
}
