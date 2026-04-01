package com.legacyminecraft.poseidon.world.entity;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.*;

public class EntityGiantZombie extends EntityMonster {

    public EntityGiantZombie(World world) {
        super(world);
        this.texture = "/mob/zombie.png";
        this.aE = 0.5F;
        this.damage = 50;
        this.health *= 10;
        this.height *= 6.0F;
        this.b(this.length * 6.0F, this.width * 6.0F);
    }

    protected float a(int i, int j, int k) {
        return this.world.n(i, j, k) - 0.5F;
    }
}
