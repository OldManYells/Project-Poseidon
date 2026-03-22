package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractEventBridgeBehaviour;
import net.minecraft.server.BlockMinecartTrack;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class MinecartItemPlacementBehaviour {
    private static final MinecartItemPlacementBehaviour INSTANCE = new MinecartItemPlacementBehaviour();
    private static final PlayerInteractEventBridgeBehaviour PLAYER_INTERACT_EVENT_BRIDGE = PlayerInteractEventBridgeBehaviour.getInstance();

    private MinecartItemPlacementBehaviour() {
    }

    public static MinecartItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean place(ItemStack itemstack, EntityHuman entityhuman, World world, int x, int y, int z, int face, int minecartType) {
        int blockId = world.getTypeId(x, y, z);
        if (!BlockMinecartTrack.c(blockId)) {
            return false;
        }

        if (!world.isStatic) {
            PlayerInteractEvent event = PLAYER_INTERACT_EVENT_BRIDGE.callPlayerInteract(
                    entityhuman,
                    Action.RIGHT_CLICK_BLOCK,
                    x,
                    y,
                    z,
                    face,
                    itemstack
            );
            if (event.isCancelled()) {
                return false;
            }
            world.addEntity(new EntityMinecart(world, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), minecartType));
        }

        --itemstack.count;
        return true;
    }
}
