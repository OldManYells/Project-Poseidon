package com.legacyminecraft.poseidon.world.block;

import com.legacyminecraft.poseidon.world.block.material.Material;
import com.legacyminecraft.poseidon.world.core.World;
import com.legacyminecraft.poseidon.world.entity.Entity;
import com.legacyminecraft.poseidon.world.item.Item;
import com.legacyminecraft.poseidon.world.physics.AxisAlignedBB;

import java.util.Random;

public class BlockWeb extends Block {

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
