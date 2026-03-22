package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnEntryBehaviour;

public class BiomeMeta {
    private static final BiomeSpawnEntryBehaviour BIOME_SPAWN_ENTRY_BEHAVIOUR = BiomeSpawnEntryBehaviour.getInstance();

    public Class a;
    public int b;

    public BiomeMeta(Class oclass, int i) {
        BIOME_SPAWN_ENTRY_BEHAVIOUR.initializeLegacyEntry(this, oclass, i);
    }

    public Class getEntityClass() {
        return BIOME_SPAWN_ENTRY_BEHAVIOUR.resolveEntityClass(this);
    }

    public int getSpawnWeight() {
        return BIOME_SPAWN_ENTRY_BEHAVIOUR.resolveSpawnWeight(this);
    }

    public void poseidonSetEntityClass(Class entityClass) {
        this.a = entityClass;
    }

    public void poseidonSetSpawnWeight(int spawnWeight) {
        this.b = spawnWeight;
    }

    public Class poseidonGetEntityClass() {
        return this.a;
    }

    public int poseidonGetSpawnWeight() {
        return this.b;
    }
}
