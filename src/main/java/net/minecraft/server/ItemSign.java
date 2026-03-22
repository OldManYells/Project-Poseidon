package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemPlacementMathBehaviour;
import com.legacyminecraft.poseidon.item.SignItemPlacementBehaviour;
// CraftBukkit start
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
// CraftBukkit end

public class ItemSign extends Item {
    private static final ItemPlacementMathBehaviour ITEM_PLACEMENT_MATH_BEHAVIOUR = ItemPlacementMathBehaviour.getInstance();
    private static final SignItemPlacementBehaviour SIGN_ITEM_PLACEMENT_BEHAVIOUR = SignItemPlacementBehaviour.getInstance();

    public ItemSign(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        if (SIGN_ITEM_PLACEMENT_BEHAVIOUR.isBottomFace(l)) {
            return false;
        } else if (!SIGN_ITEM_PLACEMENT_BEHAVIOUR.canAttachToClickedBlock(world.getMaterial(i, j, k).isBuildable())) {
            return false;
        } else {
            int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit
            ItemPlacementMathBehaviour.Position target = SIGN_ITEM_PLACEMENT_BEHAVIOUR.resolveTarget(i, j, k, l, ITEM_PLACEMENT_MATH_BEHAVIOUR);
            i = target.x;
            j = target.y;
            k = target.z;

            if (!Block.SIGN_POST.canPlace(world, i, j, k)) {
                return false;
            } else {
                CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k); // CraftBukkit
                int blockId = SIGN_ITEM_PLACEMENT_BEHAVIOUR.resolvePlacedBlockId(l, Block.SIGN_POST.id, Block.WALL_SIGN.id);
                int blockData = SIGN_ITEM_PLACEMENT_BEHAVIOUR.resolvePlacedData(l, entityhuman.yaw, ITEM_PLACEMENT_MATH_BEHAVIOUR);
                Block placedBlock = Block.byId[blockId];

                world.setTypeIdAndData(i, j, k, blockId, blockData);

                // CraftBukkit start - sign
                BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ, placedBlock);

                if (event.isCancelled() || !event.canBuild()) {
                    event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
                    return false;
                }
                // CraftBukkit end

                --itemstack.count;
                TileEntitySign tileentitysign = (TileEntitySign) world.getTileEntity(i, j, k);

                if (tileentitysign != null) {
                    entityhuman.a(tileentitysign);
                }

                return true;
            }
        }
    }
}
