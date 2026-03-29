package org.bukkit.craftbukkit.block;

import net.minecraft.server.*;
import net.minecraft.server.CraftBlock;
import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.craftbukkit.item.Item;

import java.util.Random;

public class BlockWeb extends CraftBlock {

    public BlockWeb(int i, int j) {
        super(i, j, Material.WEB);
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        entity.bf = true;
    }

    public boolean a() {
        return false;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean b() {
        return false;
    }

    public int a(int i, Random random) {
        return Item.STRING.id;
    }
}
