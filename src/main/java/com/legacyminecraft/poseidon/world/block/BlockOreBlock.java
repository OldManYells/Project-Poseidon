package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

public class BlockOreBlock extends Block {

    public BlockOreBlock(int i, int j) {
        super(i, Material.ORE);
        this.textureId = j;
    }

    public int a(int i) {
        return this.textureId;
    }
}
