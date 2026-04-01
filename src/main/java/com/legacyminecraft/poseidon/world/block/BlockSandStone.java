package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

public class BlockSandStone extends Block {

    public BlockSandStone(int i) {
        super(i, 192, Material.STONE);
    }

    public int a(int i) {
        return i == 1 ? this.textureId - 16 : (i == 0 ? this.textureId + 16 : this.textureId);
    }
}
