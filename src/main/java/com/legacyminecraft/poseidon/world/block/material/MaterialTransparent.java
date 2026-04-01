package com.legacyminecraft.poseidon.world.block.material;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class MaterialTransparent extends Material {

    public MaterialTransparent(MaterialMapColor materialmapcolor) {
        super(materialmapcolor);
        this.f();
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
