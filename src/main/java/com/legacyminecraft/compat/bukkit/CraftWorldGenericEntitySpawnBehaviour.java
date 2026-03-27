package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld generic entity spawn dispatch.
 */
public final class CraftWorldGenericEntitySpawnBehaviour {
    private static final CraftWorldGenericEntitySpawnBehaviour INSTANCE = new CraftWorldGenericEntitySpawnBehaviour();

    private CraftWorldGenericEntitySpawnBehaviour() {
    }

    public static CraftWorldGenericEntitySpawnBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T spawn(WorldServer world, Location location, Class<T> clazz) {
        if (location == null || clazz == null) {
            throw new IllegalArgumentException("Location or entity class cannot be null");
        }

        com.legacyminecraft.compat.bukkit.Entity entity = null;

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float pitch = location.getPitch();
        float yaw = location.getYaw();

        if (Boat.class.isAssignableFrom(clazz)) {
            entity = new EntityBoat(world, x, y, z);
        } else if (Egg.class.isAssignableFrom(clazz)) {
            entity = new EntityEgg(world, x, y, z);
        } else if (FallingSand.class.isAssignableFrom(clazz)) {
            entity = new EntityFallingSand(world, x, y, z, 0);
        } else if (Fireball.class.isAssignableFrom(clazz)) {
            entity = new EntityFireball(world);
            ((EntityFireball) entity).setPositionRotation(x, y, z, yaw, pitch);
            Vector direction = location.getDirection().multiply(10);
            ((EntityFireball) entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
        } else if (Snowball.class.isAssignableFrom(clazz)) {
            entity = new EntitySnowball(world, x, y, z);
        } else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecart(world, x, y, z, CraftMinecart.Type.PoweredMinecart.getId());
            } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecart(world, x, y, z, CraftMinecart.Type.StorageMinecart.getId());
            } else {
                entity = new EntityMinecart(world, x, y, z, CraftMinecart.Type.Minecart.getId());
            }
        } else if (Arrow.class.isAssignableFrom(clazz)) {
            entity = new EntityArrow(world);
            entity.setPositionRotation(x, y, z, 0, 0);
        } else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
                entity = new EntityChicken(world);
            } else if (Cow.class.isAssignableFrom(clazz)) {
                entity = new EntityCow(world);
            } else if (Creeper.class.isAssignableFrom(clazz)) {
                entity = new EntityCreeper(world);
            } else if (Ghast.class.isAssignableFrom(clazz)) {
                entity = new EntityGhast(world);
            } else if (Pig.class.isAssignableFrom(clazz)) {
                entity = new EntityPig(world);
            } else if (Player.class.isAssignableFrom(clazz)) {
                // Intentionally unsupported in this generic spawn path.
            } else if (Sheep.class.isAssignableFrom(clazz)) {
                entity = new EntitySheep(world);
            } else if (Skeleton.class.isAssignableFrom(clazz)) {
                entity = new EntitySkeleton(world);
            } else if (Slime.class.isAssignableFrom(clazz)) {
                entity = new EntitySlime(world);
            } else if (Spider.class.isAssignableFrom(clazz)) {
                entity = new EntitySpider(world);
            } else if (Squid.class.isAssignableFrom(clazz)) {
                entity = new EntitySquid(world);
            } else if (Wolf.class.isAssignableFrom(clazz)) {
                entity = new EntityWolf(world);
            } else if (PigZombie.class.isAssignableFrom(clazz)) {
                entity = new EntityPigZombie(world);
            } else if (Zombie.class.isAssignableFrom(clazz)) {
                entity = new EntityZombie(world);
            }

            if (entity != null) {
                entity.setLocation(x, y, z, pitch, yaw);
            }
        } else if (Painting.class.isAssignableFrom(clazz)) {
            // Intentionally unsupported in this generic spawn path.
        } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new EntityTNTPrimed(world, x, y, z);
        } else if (Weather.class.isAssignableFrom(clazz)) {
            entity = new EntityWeatherStorm(world, x, y, z);
        } else if (LightningStrike.class.isAssignableFrom(clazz)) {
            // Intentionally unsupported in this generic spawn path.
        } else if (Fish.class.isAssignableFrom(clazz)) {
            entity = new EntityFish(world);
            entity.setLocation(x, y, z, pitch, yaw);
        }

        if (entity == null) {
            throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
        }

        world.addEntity(entity);
        return (T) entity.getBukkitEntity();
    }
}
