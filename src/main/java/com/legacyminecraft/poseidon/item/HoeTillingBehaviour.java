package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.compat.bukkit.BlockPlaceEventBridgeBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockPlaceEvent;

public final class HoeTillingBehaviour {
    private static final HoeTillingBehaviour INSTANCE = new HoeTillingBehaviour();
    private static final BlockPlaceEventBridgeBehaviour BLOCK_PLACE_EVENT_BRIDGE = BlockPlaceEventBridgeBehaviour.getInstance();

    private HoeTillingBehaviour() {
    }

    public static HoeTillingBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean till(ItemStack itemstack, EntityHuman entityhuman, World world, int x, int y, int z, int face) {
        int blockId = world.getTypeId(x, y, z);
        int aboveBlockId = world.getTypeId(x, y + 1, z);

        if ((face == 0 || aboveBlockId != 0 || blockId != Block.GRASS.id) && blockId != Block.DIRT.id) {
            return false;
        }

        Block farmland = Block.SOIL;
        world.makeSound((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
                farmland.stepSound.getName(), (farmland.stepSound.getVolume1() + 1.0F) / 2.0F, farmland.stepSound.getVolume2() * 0.8F);

        if (world.isStatic) {
            return true;
        }

        BlockState blockState = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world, x, y, z);
        world.setTypeId(x, y, z, farmland.id);
        BlockPlaceEvent event = BLOCK_PLACE_EVENT_BRIDGE.callBlockPlaceEvent(world, entityhuman, blockState, x, y, z, farmland);
        if (event.isCancelled() || !event.canBuild()) {
            event.getBlockPlaced().setTypeId(blockState.getTypeId());
            return false;
        }

        itemstack.damage(1, entityhuman);
        return true;
    }
}
