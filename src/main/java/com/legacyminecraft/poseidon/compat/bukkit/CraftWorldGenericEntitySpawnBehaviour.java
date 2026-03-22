package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityEgg;
import net.minecraft.server.EntityFallingSand;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityFish;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntitySquid;
import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingSand;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Weather;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

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

        net.minecraft.server.Entity entity = null;

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
