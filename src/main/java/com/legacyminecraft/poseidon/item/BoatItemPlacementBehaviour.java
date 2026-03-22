package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractEventBridgeBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.World;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class BoatItemPlacementBehaviour {
    private static final BoatItemPlacementBehaviour INSTANCE = new BoatItemPlacementBehaviour();
    private static final ItemUseRayTraceBehaviour ITEM_USE_RAYTRACE_BEHAVIOUR = ItemUseRayTraceBehaviour.getInstance();
    private static final PlayerInteractEventBridgeBehaviour PLAYER_INTERACT_EVENT_BRIDGE = PlayerInteractEventBridgeBehaviour.getInstance();

    private BoatItemPlacementBehaviour() {
    }

    public static BoatItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack use(ItemStack itemstack, World world, EntityHuman entityhuman) {
        MovingObjectPosition movingobjectposition = ITEM_USE_RAYTRACE_BEHAVIOUR.rayTraceFromPlayer(world, entityhuman, true);
        if (movingobjectposition == null || movingobjectposition.type != EnumMovingObjectType.TILE) {
            return itemstack;
        }

        int x = movingobjectposition.b;
        int y = movingobjectposition.c;
        int z = movingobjectposition.d;

        if (!world.isStatic) {
            PlayerInteractEvent event = PLAYER_INTERACT_EVENT_BRIDGE.callPlayerInteract(
                    entityhuman,
                    Action.RIGHT_CLICK_BLOCK,
                    x,
                    y,
                    z,
                    movingobjectposition.face,
                    itemstack
            );
            if (event.isCancelled()) {
                return itemstack;
            }

            int spawnY = y;
            if (world.getTypeId(x, y, z) == Block.SNOW.id) {
                --spawnY;
            }
            world.addEntity(new EntityBoat(world, (double) ((float) x + 0.5F), (double) ((float) spawnY + 1.0F), (double) ((float) z + 0.5F)));
        }

        --itemstack.count;
        return itemstack;
    }
}
