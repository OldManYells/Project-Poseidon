package org.bukkit.craftbukkit.block;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.craftbukkit.entity.Entity;

public class BlockSlowSand extends CraftBlock {

    public BlockSlowSand(int i, int j) {
        super(i, j, Material.SAND);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        float f = 0.125F;

        return AxisAlignedBB.b((double) i, (double) j, (double) k, (double) (i + 1), (double) ((float) (j + 1) - f), (double) (k + 1));
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        entity.motX *= 0.4D;
        entity.motZ *= 0.4D;
    }
}
