package org.bukkit.craftbukkit.item;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.World;
import org.bukkit.craftbukkit.block.BlockJukeBox;
import org.bukkit.craftbukkit.entity.EntityHuman;

public class ItemRecord extends Item {

    public final String a;

    protected ItemRecord(int i, String s) {
        super(i);
        this.a = s;
        this.maxStackSize = 1;
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        if (world.getTypeId(i, j, k) == CraftBlock.JUKEBOX.id && world.getData(i, j, k) == 0) {
            if (world.isStatic) {
                return true;
            } else {
                ((BlockJukeBox) CraftBlock.JUKEBOX).f(world, i, j, k, this.id);
                world.a((EntityHuman) null, 1005, i, j, k, this.id);
                --itemstack.count;
                return true;
            }
        } else {
            return false;
        }
    }
}
