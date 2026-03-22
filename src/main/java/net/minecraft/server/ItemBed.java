package net.minecraft.server;

import com.legacyminecraft.poseidon.item.BedItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.ItemPlacementMathBehaviour;
// CraftBukkit start
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
// CraftBukkit end

public class ItemBed extends Item {
    private static final ItemPlacementMathBehaviour ITEM_PLACEMENT_MATH_BEHAVIOUR = ItemPlacementMathBehaviour.getInstance();
    private static final BedItemPlacementBehaviour BED_ITEM_PLACEMENT_BEHAVIOUR = BedItemPlacementBehaviour.getInstance();

    public ItemBed(int i) {
        super(i);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        if (!ITEM_PLACEMENT_MATH_BEHAVIOUR.isTopFace(l)) {
            return false;
        } else {
            int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit

            ++j;
            BlockBed blockbed = (BlockBed) Block.BED;
            int i1 = BED_ITEM_PLACEMENT_BEHAVIOUR.resolveFacingFromYaw(entityhuman.yaw, ITEM_PLACEMENT_MATH_BEHAVIOUR);
            ItemPlacementMathBehaviour.Offset offset = BED_ITEM_PLACEMENT_BEHAVIOUR.resolveOffset(i1, ITEM_PLACEMENT_MATH_BEHAVIOUR);

            if (BED_ITEM_PLACEMENT_BEHAVIOUR.canPlaceBed(world.isEmpty(i, j, k), world.isEmpty(i + offset.x, j, k + offset.z), world.e(i, j - 1, k), world.e(i + offset.x, j - 1, k + offset.z))) {
                CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k); // CraftBukkit

                world.setTypeIdAndData(i, j, k, blockbed.id, i1);

                // CraftBukkit start - bed
                BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, blockbed);

                if (event.isCancelled() || !event.canBuild()) {
                    event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
                    return false;
                }
                // CraftBukkit end

                world.setTypeIdAndData(i + offset.x, j, k + offset.z, blockbed.id, BED_ITEM_PLACEMENT_BEHAVIOUR.resolveHeadPartData(i1));
                --itemstack.count;
                return true;
            } else {
                return false;
            }
        }
    }
}
