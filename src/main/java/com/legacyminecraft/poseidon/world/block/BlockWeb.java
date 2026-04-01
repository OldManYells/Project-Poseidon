package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.item.Item;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

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
