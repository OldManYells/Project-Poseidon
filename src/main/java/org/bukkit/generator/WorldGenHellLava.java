package org.bukkit.generator;


import net.minecraft.server.CraftBlock;
import org.bukkit.craftbukkit.world.World;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator {

    private int a;

    public WorldGenHellLava(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        if (world.getTypeId(i, j + 1, k) != CraftBlock.NETHERRACK.id) {
            return false;
        } else if (world.getTypeId(i, j, k) != 0 && world.getTypeId(i, j, k) != CraftBlock.NETHERRACK.id) {
            return false;
        } else {
            int l = 0;

            if (world.getTypeId(i - 1, j, k) == CraftBlock.NETHERRACK.id) {
                ++l;
            }

            if (world.getTypeId(i + 1, j, k) == CraftBlock.NETHERRACK.id) {
                ++l;
            }

            if (world.getTypeId(i, j, k - 1) == CraftBlock.NETHERRACK.id) {
                ++l;
            }

            if (world.getTypeId(i, j, k + 1) == CraftBlock.NETHERRACK.id) {
                ++l;
            }

            if (world.getTypeId(i, j - 1, k) == CraftBlock.NETHERRACK.id) {
                ++l;
            }

            int i1 = 0;

            if (world.isEmpty(i - 1, j, k)) {
                ++i1;
            }

            if (world.isEmpty(i + 1, j, k)) {
                ++i1;
            }

            if (world.isEmpty(i, j, k - 1)) {
                ++i1;
            }

            if (world.isEmpty(i, j, k + 1)) {
                ++i1;
            }

            if (world.isEmpty(i, j - 1, k)) {
                ++i1;
            }

            if (l == 4 && i1 == 1) {
                world.setTypeId(i, j, k, this.a);
                world.a = true;
                CraftBlock.byId[this.a].a(world, i, j, k, random);
                world.a = false;
            }

            return true;
        }
    }
}
