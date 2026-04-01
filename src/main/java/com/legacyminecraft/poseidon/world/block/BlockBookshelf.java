package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockBookshelf extends Block {

    public BlockBookshelf(int i, int j) {
        super(i, j, Material.WOOD);
    }

    public int a(int i) {
        return i <= 1 ? 4 : this.textureId;
    }

    public int a(Random random) {
        return 0;
    }
}
