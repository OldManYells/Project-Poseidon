package com.legacyminecraft.poseidon.world.generation;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

public class BiomeHell extends BiomeBase {

    public BiomeHell() {
        this.s.clear();
        this.t.clear();
        this.u.clear();
        this.s.add(new BiomeMeta(EntityGhast.class, 10));
        this.s.add(new BiomeMeta(EntityPigZombie.class, 10));
    }
}
