package org.bukkit.craftbukkit.item;

// CraftBukkit start
import net.minecraft.server.CraftBlock;
import net.minecraft.server.EnumToolMaterial;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
// CraftBukkit end

public class ItemHoe extends Item {

    public ItemHoe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        int i1 = world.getTypeId(i, j, k);
        int j1 = world.getTypeId(i, j + 1, k);

        if ((l == 0 || j1 != 0 || i1 != CraftBlock.GRASS.id) && i1 != CraftBlock.DIRT.id) {
            return false;
        } else {
            CraftBlock baseBlock = CraftBlock.SOIL;

            world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), baseBlock.stepSound.getName(), (baseBlock.stepSound.getVolume1() + 1.0F) / 2.0F, baseBlock.stepSound.getVolume2() * 0.8F);
            if (world.isStatic) {
                return true;
            } else {
                CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k); // CraftBukkit

                world.setTypeId(i, j, k, baseBlock.id);

                // CraftBukkit start - Hoes - blockface -1 for 'SELF'
                BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, i, j, k, baseBlock);

                if (event.isCancelled() || !event.canBuild()) {
                    event.getBlockPlaced().setTypeId(blockState.getTypeId());
                    return false;
                }
                // CraftBukkit end

                itemstack.damage(1, entityhuman);
                return true;
            }
        }
    }
}
