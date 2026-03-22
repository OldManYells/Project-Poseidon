package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.BiomeMeta;
import net.minecraft.server.BlockBed;
import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.ChunkPosition;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.EnumCreatureType;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.PathEntity;
import net.minecraft.server.PathPoint;
import net.minecraft.server.Pathfinder;
import net.minecraft.server.World;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Canonical natural/sleep mob spawning behaviour.
 */
public final class CreatureSpawnBehaviour {
    private static final CreatureSpawnBehaviour INSTANCE = new CreatureSpawnBehaviour();
    private static final Class[] SLEEP_SPAWNER_MOBS = new Class[]{EntitySpider.class, EntityZombie.class, EntitySkeleton.class};

    private final Set<ChunkCoordIntPair> candidateChunks = new HashSet<ChunkCoordIntPair>();

    private CreatureSpawnBehaviour() {
    }

    public static CreatureSpawnBehaviour getInstance() {
        return INSTANCE;
    }

    public int spawnEntities(World world, boolean allowHostiles, boolean allowPeaceful) {
        if (!allowHostiles && !allowPeaceful) {
            return 0;
        }

        candidateChunks.clear();

        for (int playerIndex = 0; playerIndex < world.players.size(); ++playerIndex) {
            EntityHuman player = (EntityHuman) world.players.get(playerIndex);
            int playerChunkX = MathHelper.floor(player.locX / 16.0D);
            int playerChunkZ = MathHelper.floor(player.locZ / 16.0D);
            final int chunkRadius = 8;

            for (int deltaChunkX = -chunkRadius; deltaChunkX <= chunkRadius; ++deltaChunkX) {
                for (int deltaChunkZ = -chunkRadius; deltaChunkZ <= chunkRadius; ++deltaChunkZ) {
                    candidateChunks.add(new ChunkCoordIntPair(playerChunkX + deltaChunkX, playerChunkZ + deltaChunkZ));
                }
            }
        }

        int totalSpawnedCount = 0;
        ChunkCoordinates worldSpawn = world.getSpawn();
        EnumCreatureType[] creatureTypes = EnumCreatureType.values();

        for (EnumCreatureType creatureType : creatureTypes) {
            if ((creatureType.isPeaceful() && !allowPeaceful) || (!creatureType.isPeaceful() && !allowHostiles)) {
                continue;
            }

            if (world.a(creatureType.getBaseClass()) > creatureType.getMaxCount() * candidateChunks.size() / 256) {
                continue;
            }

            Iterator<ChunkCoordIntPair> candidateIterator = candidateChunks.iterator();

            chunksLoop:
            while (candidateIterator.hasNext()) {
                ChunkCoordIntPair chunkPosition = candidateIterator.next();
                BiomeBase biome = world.getWorldChunkManager().a(chunkPosition);
                List<BiomeMeta> spawnEntries = biome.a(creatureType);

                if (spawnEntries == null || spawnEntries.isEmpty()) {
                    continue;
                }

                int totalWeight = 0;
                for (BiomeMeta entry : spawnEntries) {
                    totalWeight += entry.getSpawnWeight();
                }

                int selection = world.random.nextInt(totalWeight);
                BiomeMeta selectedEntry = spawnEntries.get(0);
                for (BiomeMeta entry : spawnEntries) {
                    selection -= entry.getSpawnWeight();
                    if (selection < 0) {
                        selectedEntry = entry;
                        break;
                    }
                }

                ChunkPosition randomChunkBlock = pickRandomBlockInChunkArea(world, chunkPosition.x * 16, chunkPosition.z * 16);
                int baseX = randomChunkBlock.x;
                int baseY = randomChunkBlock.y;
                int baseZ = randomChunkBlock.z;

                if (!world.e(baseX, baseY, baseZ) && world.getMaterial(baseX, baseY, baseZ) == creatureType.getSpawnMaterial()) {
                    int groupSpawnedCount = 0;

                    for (int groupAttempt = 0; groupAttempt < 3; ++groupAttempt) {
                        int x = baseX;
                        int y = baseY;
                        int z = baseZ;
                        final byte groupSpread = 6;

                        for (int attempt = 0; attempt < 4; ++attempt) {
                            x += world.random.nextInt(groupSpread) - world.random.nextInt(groupSpread);
                            y += world.random.nextInt(1) - world.random.nextInt(1);
                            z += world.random.nextInt(groupSpread) - world.random.nextInt(groupSpread);

                            if (canSpawnHere(creatureType, world, x, y, z)) {
                                float spawnX = x + 0.5F;
                                float spawnY = y;
                                float spawnZ = z + 0.5F;

                                if (world.a(spawnX, spawnY, spawnZ, 24.0D) == null) {
                                    float dx = spawnX - worldSpawn.x;
                                    float dy = spawnY - worldSpawn.y;
                                    float dz = spawnZ - worldSpawn.z;
                                    float distanceSquared = dx * dx + dy * dy + dz * dz;

                                    if (distanceSquared >= 576.0F) {
                                        EntityLiving mob;

                                        try {
                                            mob = (EntityLiving) selectedEntry.getEntityClass().getConstructor(World.class).newInstance(world);
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            return totalSpawnedCount;
                                        }

                                        mob.setPositionRotation(spawnX, spawnY, spawnZ, world.random.nextFloat() * 360.0F, 0.0F);

                                        if (mob.d()) {
                                            ++groupSpawnedCount;
                                            world.addEntity(mob, SpawnReason.NATURAL);
                                            applyPostSpawnExtras(mob, world, spawnX, spawnY, spawnZ);

                                            if (groupSpawnedCount >= mob.l()) {
                                                totalSpawnedCount += groupSpawnedCount;
                                                continue chunksLoop;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    totalSpawnedCount += groupSpawnedCount;
                }
            }
        }

        return totalSpawnedCount;
    }

    public ChunkPosition pickRandomBlockInChunkArea(World world, int chunkBaseX, int chunkBaseZ) {
        int x = chunkBaseX + world.random.nextInt(16);
        int y = world.random.nextInt(128);
        int z = chunkBaseZ + world.random.nextInt(16);

        return new ChunkPosition(x, y, z);
    }

    public boolean canSpawnHere(EnumCreatureType creatureType, World world, int x, int y, int z) {
        if (creatureType.getSpawnMaterial() == Material.WATER) {
            return world.getMaterial(x, y, z).isLiquid() && !world.e(x, y + 1, z);
        }

        return world.e(x, y - 1, z)
                && !world.e(x, y, z)
                && !world.getMaterial(x, y, z).isLiquid()
                && !world.e(x, y + 1, z);
    }

    public void applyPostSpawnExtras(EntityLiving entity, World world, float x, float y, float z) {
        if (entity instanceof EntitySpider && world.random.nextInt(100) == 0) {
            EntitySkeleton skeletonRider = new EntitySkeleton(world);

            skeletonRider.setPositionRotation((double) x, (double) y, (double) z, entity.yaw, 0.0F);
            world.addEntity(skeletonRider, SpawnReason.NATURAL);
            skeletonRider.mount(entity);
        } else if (entity instanceof EntitySheep) {
            ((EntitySheep) entity).setColor(EntitySheep.a(world.random));
        }
    }

    public boolean spawnSleepThreats(World world, List<EntityHuman> players) {
        boolean anySpawned = false;
        Pathfinder pathfinder = new Pathfinder(world);

        for (Object object : players) {
            EntityHuman player = (EntityHuman) object;
            Class<?>[] candidates = SLEEP_SPAWNER_MOBS;

            if (candidates == null || candidates.length == 0) {
                continue;
            }

            boolean spawnedNearPlayer = false;

            for (int attempt = 0; attempt < 20 && !spawnedNearPlayer; ++attempt) {
                int x = MathHelper.floor(player.locX) + world.random.nextInt(32) - world.random.nextInt(32);
                int z = MathHelper.floor(player.locZ) + world.random.nextInt(32) - world.random.nextInt(32);
                int y = MathHelper.floor(player.locY) + world.random.nextInt(16) - world.random.nextInt(16);

                if (y < 1) {
                    y = 1;
                } else if (y > 128) {
                    y = 128;
                }

                int groundY;
                for (groundY = y; groundY > 2 && !world.e(x, groundY - 1, z); --groundY) {
                    // Find ground.
                }

                while (!canSpawnHere(EnumCreatureType.MONSTER, world, x, groundY, z) && groundY < y + 16 && groundY < 128) {
                    ++groundY;
                }

                if (groundY >= y + 16 || groundY >= 128) {
                    continue;
                }

                float spawnX = x + 0.5F;
                float spawnY = groundY;
                float spawnZ = z + 0.5F;

                int selectedIndex = world.random.nextInt(candidates.length);
                EntityLiving mob;
                try {
                    mob = (EntityLiving) candidates[selectedIndex].getConstructor(World.class).newInstance(world);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return anySpawned;
                }

                mob.setPositionRotation(spawnX, spawnY, spawnZ, world.random.nextFloat() * 360.0F, 0.0F);

                if (mob.d()) {
                    PathEntity pathToPlayer = pathfinder.a(mob, player, 32.0F);
                    if (pathToPlayer != null && pathToPlayer.a > 1) {
                        PathPoint firstStep = pathToPlayer.c();
                        if (Math.abs(firstStep.a - player.locX) < 1.5D
                                && Math.abs(firstStep.c - player.locZ) < 1.5D
                                && Math.abs(firstStep.b - player.locY) < 1.5D) {
                            ChunkCoordinates bedCoordinates = BlockBed.f(world,
                                    MathHelper.floor(player.locX),
                                    MathHelper.floor(player.locY),
                                    MathHelper.floor(player.locZ), 1);

                            if (bedCoordinates == null) {
                                bedCoordinates = new ChunkCoordinates(x, groundY + 1, z);
                            }

                            mob.setPositionRotation(bedCoordinates.x + 0.5F, bedCoordinates.y, bedCoordinates.z + 0.5F, 0.0F, 0.0F);
                            world.addEntity(mob, SpawnReason.BED);
                            applyPostSpawnExtras(mob, world, bedCoordinates.x + 0.5F, bedCoordinates.y, bedCoordinates.z + 0.5F);

                            player.a(true, false, false);
                            mob.Q();

                            anySpawned = true;
                            spawnedNearPlayer = true;
                        }
                    }
                }
            }
        }

        return anySpawned;
    }
}
