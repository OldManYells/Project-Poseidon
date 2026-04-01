package com.legacyminecraft.poseidon.world.block.material;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class MaterialLogic extends Material {

    public MaterialLogic(MaterialMapColor materialmapcolor) {
        super(materialmapcolor);
    }

    public boolean isBuildable() {
        return false;
    }

    public boolean blocksLight() {
        return false;
    }

    public boolean isSolid() {
        return false;
    }
}
