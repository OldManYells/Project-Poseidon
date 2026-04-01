package com.legacyminecraft.poseidon.world.generation;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

public class BiomeSky extends BiomeBase {

    public BiomeSky() {
        this.s.clear();
        this.t.clear();
        this.u.clear();
        this.t.add(new BiomeMeta(EntityChicken.class, 10));
    }
}
