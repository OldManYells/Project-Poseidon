package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.CreatureSpawnBehaviour;

import java.util.List;

// CraftBukkit
public final class SpawnerCreature {
    private static final CreatureSpawnBehaviour CREATURE_SPAWN_BEHAVIOUR = CreatureSpawnBehaviour.getInstance();

    public SpawnerCreature() {
    }

    public static int spawnEntities(World world, boolean allowHostiles, boolean allowPeaceful) {
        return CREATURE_SPAWN_BEHAVIOUR.spawnEntities(world, allowHostiles, allowPeaceful);
    }

    protected static ChunkPosition pickRandomBlockInChunkArea(World world, int i, int j) {
        return CREATURE_SPAWN_BEHAVIOUR.pickRandomBlockInChunkArea(world, i, j);
    }

    private static boolean canSpawnHere(EnumCreatureType type, World world, int x, int y, int z) {
        return CREATURE_SPAWN_BEHAVIOUR.canSpawnHere(type, world, x, y, z);
    }

    private static void applyPostSpawnExtras(EntityLiving entityliving, World world, float f, float f1, float f2) {
        CREATURE_SPAWN_BEHAVIOUR.applyPostSpawnExtras(entityliving, world, f, f1, f2);
    }

    public static boolean spawnSleepThreats(World world, List<EntityHuman> listPlayers) {
        return CREATURE_SPAWN_BEHAVIOUR.spawnSleepThreats(world, listPlayers);
    }

    /* ------------------------------------------------------------
     * Compatibility
     * ------------------------------------------------------------ */

    // Old: protected static ChunkPosition a(World, int, int)
    protected static ChunkPosition a(World world, int i, int j) {
        return pickRandomBlockInChunkArea(world, i, j);
    }

    // Old: private static boolean a(EnumCreatureType, World, int, int, int)
    private static boolean a(EnumCreatureType type, World world, int x, int y, int z) {
        return canSpawnHere(type, world, x, y, z);
    }

    // Old: private static void a(EntityLiving, World, float, float, float)
    private static void a(EntityLiving entity, World world, float x, float y, float z) {
        applyPostSpawnExtras(entity, world, x, y, z);
    }

    // Old: public static boolean a(World, List)
    public static boolean a(World world, List list) {
        return spawnSleepThreats(world, list);
    }
}
