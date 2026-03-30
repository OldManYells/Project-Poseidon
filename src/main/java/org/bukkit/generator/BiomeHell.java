package org.bukkit.generator;

import org.bukkit.craftbukkit.entity.EntityGhast;
import org.bukkit.craftbukkit.entity.EntityPigZombie;

public class BiomeHell extends BiomeBase {

    public BiomeHell() {
        this.s.clear();
        this.t.clear();
        this.u.clear();
        this.s.add(new BiomeMeta(EntityGhast.class, 10));
        this.s.add(new BiomeMeta(EntityPigZombie.class, 10));
    }
}
