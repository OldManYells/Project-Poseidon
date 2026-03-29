package org.bukkit.craftbukkit.item;

// CraftBukkit start
import net.minecraft.server.CraftBlock;
import net.minecraft.server.World;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
// CraftBukkit end

public class ItemReed extends Item {

    private int id;

    public ItemReed(int i, CraftBlock baseBlock) {
        super(i);
        this.id = baseBlock.id;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit

        if (world.getTypeId(i, j, k) == CraftBlock.SNOW.id) {
            l = 0;
        } else {
            if (l == 0) {
                --j;
            }

            if (l == 1) {
                ++j;
            }

            if (l == 2) {
                --k;
            }

            if (l == 3) {
                ++k;
            }

            if (l == 4) {
                --i;
            }

            if (l == 5) {
                ++i;
            }
        }

        if (itemstack.count == 0) {
            return false;
        } else {
            if (world.a(this.id, i, j, k, false, l)) {
                CraftBlock baseBlock = CraftBlock.byId[this.id];

                // CraftBukkit start - This executes the placement of the block
                CraftBlockState replacedBlockState = CraftBlockState.getBlockState(world, i, j, k); // CraftBukkit
                /**
                 * @see net.minecraft.server.World#setTypeId(int i, int j, int k, int l)
                 *
                 * This replaces world.setTypeId(IIII), we're doing this because we need to
                 * hook between the 'placement' and the informing to 'world' so we can
                 * sanely undo this.
                 *
                 * Whenever the call to 'world.setTypeId' changes we need to figure out again what to
                 * replace this with.
                 */
                if (world.setRawTypeId(i, j, k, this.id)) { // <-- world.e does this to place the block
                    BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, replacedBlockState, clickedX, clickedY, clickedZ, baseBlock);

                    if (event.isCancelled() || !event.canBuild()) {
                        // CraftBukkit - undo; this only has reed, repeater and pie blocks
                        world.setTypeIdAndData(i, j, k, replacedBlockState.getTypeId(), replacedBlockState.getRawData());

                        return true;
                    }

                    world.update(i, j, k, this.id); // <-- world.setTypeId does this on success (tell the world)
                    // CraftBukkit end

                    CraftBlock.byId[this.id].postPlace(world, i, j, k, l);
                    CraftBlock.byId[this.id].postPlace(world, i, j, k, entityhuman);
                    world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), baseBlock.stepSound.getName(), (baseBlock.stepSound.getVolume1() + 1.0F) / 2.0F, baseBlock.stepSound.getVolume2() * 0.8F);
                    --itemstack.count;
                }
            }

            return true;
        }
    }
}
