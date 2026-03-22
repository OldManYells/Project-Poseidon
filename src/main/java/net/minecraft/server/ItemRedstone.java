package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemPlacementMathBehaviour;
import com.legacyminecraft.poseidon.item.RedstoneItemPlacementBehaviour;
// CraftBukkit start
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
// CraftBukkit end

public class ItemRedstone extends Item {
    private static final ItemPlacementMathBehaviour ITEM_PLACEMENT_MATH_BEHAVIOUR = ItemPlacementMathBehaviour.getInstance();
    private static final RedstoneItemPlacementBehaviour REDSTONE_ITEM_PLACEMENT_BEHAVIOUR = RedstoneItemPlacementBehaviour.getInstance();

    public ItemRedstone(int i) {
        super(i);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit
        int clickedTypeId = world.getTypeId(i, j, k);
        ItemPlacementMathBehaviour.Position target = REDSTONE_ITEM_PLACEMENT_BEHAVIOUR.resolveTarget(i, j, k, l, clickedTypeId, Block.SNOW.id, ITEM_PLACEMENT_MATH_BEHAVIOUR);
        i = target.x;
        j = target.y;
        k = target.z;

        if (REDSTONE_ITEM_PLACEMENT_BEHAVIOUR.requiresEmptyTarget(clickedTypeId, Block.SNOW.id, ITEM_PLACEMENT_MATH_BEHAVIOUR)) {
            if (!world.isEmpty(i, j, k)) {
                return false;
            }
        }

        if (Block.REDSTONE_WIRE.canPlace(world, i, j, k)) {
            CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k); // CraftBukkit

            world.setRawTypeId(i, j, k, Block.REDSTONE_WIRE.id); // CraftBukkit - We update after the event

            // CraftBukkit start - redstone
            BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, Block.REDSTONE_WIRE);

            if (event.isCancelled() || !event.canBuild()) {
                event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
                return false;
            }

            world.update( i, j, k, Block.REDSTONE_WIRE.id); // Must take place after BlockPlaceEvent, we need to update all other blocks.
            // CraftBukkit end

            --itemstack.count; // CraftBukkit - ORDER MATTERS
        }

        return true;
    }
}
