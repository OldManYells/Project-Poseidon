package net.minecraft.server;

import com.legacyminecraft.poseidon.item.DoorItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.ItemPlacementMathBehaviour;
// CraftBukkit start
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
// CraftBukkit end

public class ItemDoor extends Item {

    private Material a;
    private static final ItemPlacementMathBehaviour ITEM_PLACEMENT_MATH_BEHAVIOUR = ItemPlacementMathBehaviour.getInstance();
    private static final DoorItemPlacementBehaviour DOOR_ITEM_PLACEMENT_BEHAVIOUR = DoorItemPlacementBehaviour.getInstance();

    public ItemDoor(int i, Material material) {
        super(i);
        this.a = material;
        this.maxStackSize = 1;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        if (!ITEM_PLACEMENT_MATH_BEHAVIOUR.isTopFace(l)) {
            return false;
        } else {
            int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit

            ++j;
            int doorBlockId = DOOR_ITEM_PLACEMENT_BEHAVIOUR.resolveDoorBlockId(this.a == Material.WOOD, Block.WOODEN_DOOR.id, Block.IRON_DOOR_BLOCK.id);
            Block block = Block.byId[doorBlockId];

            if (!block.canPlace(world, i, j, k)) {
                return false;
            } else {
                int i1 = DOOR_ITEM_PLACEMENT_BEHAVIOUR.resolveFacingFromYaw(entityhuman.yaw, ITEM_PLACEMENT_MATH_BEHAVIOUR);
                ItemPlacementMathBehaviour.Offset offset = DOOR_ITEM_PLACEMENT_BEHAVIOUR.resolveOffset(i1, ITEM_PLACEMENT_MATH_BEHAVIOUR);

                int j1 = DOOR_ITEM_PLACEMENT_BEHAVIOUR.countSupport(world.e(i - offset.x, j, k - offset.z), world.e(i - offset.x, j + 1, k - offset.z));
                int k1 = DOOR_ITEM_PLACEMENT_BEHAVIOUR.countSupport(world.e(i + offset.x, j, k + offset.z), world.e(i + offset.x, j + 1, k + offset.z));
                boolean flag = DOOR_ITEM_PLACEMENT_BEHAVIOUR.hasDoorHalf(world.getTypeId(i - offset.x, j, k - offset.z) == block.id, world.getTypeId(i - offset.x, j + 1, k - offset.z) == block.id);
                boolean flag1 = DOOR_ITEM_PLACEMENT_BEHAVIOUR.hasDoorHalf(world.getTypeId(i + offset.x, j, k + offset.z) == block.id, world.getTypeId(i + offset.x, j + 1, k + offset.z) == block.id);
                int doorData = DOOR_ITEM_PLACEMENT_BEHAVIOUR.resolveBottomData(i1, DOOR_ITEM_PLACEMENT_BEHAVIOUR.shouldMirrorHinge(flag, flag1, j1, k1));

                CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k); // CraftBukkit

                world.suppressPhysics = true;
                world.setTypeIdAndData(i, j, k, block.id, doorData);

                // CraftBukkit start - bed
                world.suppressPhysics = false;
                world.applyPhysics(i, j, k, Block.REDSTONE_WIRE.id);
                BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, block);

                if (event.isCancelled() || !event.canBuild()) {
                    event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
                    return false;
                }

                world.suppressPhysics = true;
                // CraftBukkit end
                world.setTypeIdAndData(i, j + 1, k, block.id, DOOR_ITEM_PLACEMENT_BEHAVIOUR.resolveTopData(doorData));
                world.suppressPhysics = false;
                // world.applyPhysics(i, j, k, block.id); // CraftBukkit - moved up
                world.applyPhysics(i, j + 1, k, Block.REDSTONE_WIRE.id);
                --itemstack.count;
                return true;
            }
        }
    }
}
