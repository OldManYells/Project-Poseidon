package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityAnimal;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityEgg;
import net.minecraft.server.EntityFallingSand;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityFish;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityGiantZombie;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntitySquid;
import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.EntityWaterAnimal;
import net.minecraft.server.EntityWeather;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftAnimals;
import org.bukkit.craftbukkit.entity.CraftArrow;
import org.bukkit.craftbukkit.entity.CraftBoat;
import org.bukkit.craftbukkit.entity.CraftChicken;
import org.bukkit.craftbukkit.entity.CraftCow;
import org.bukkit.craftbukkit.entity.CraftCreature;
import org.bukkit.craftbukkit.entity.CraftCreeper;
import org.bukkit.craftbukkit.entity.CraftEgg;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftFallingSand;
import org.bukkit.craftbukkit.entity.CraftFireball;
import org.bukkit.craftbukkit.entity.CraftFish;
import org.bukkit.craftbukkit.entity.CraftFlying;
import org.bukkit.craftbukkit.entity.CraftGhast;
import org.bukkit.craftbukkit.entity.CraftGiant;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.craftbukkit.entity.CraftLightningStrike;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.craftbukkit.entity.CraftMonster;
import org.bukkit.craftbukkit.entity.CraftPainting;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.craftbukkit.entity.CraftPigZombie;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.entity.CraftPoweredMinecart;
import org.bukkit.craftbukkit.entity.CraftSheep;
import org.bukkit.craftbukkit.entity.CraftSkeleton;
import org.bukkit.craftbukkit.entity.CraftSlime;
import org.bukkit.craftbukkit.entity.CraftSnowball;
import org.bukkit.craftbukkit.entity.CraftSpider;
import org.bukkit.craftbukkit.entity.CraftSquid;
import org.bukkit.craftbukkit.entity.CraftStorageMinecart;
import org.bukkit.craftbukkit.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.entity.CraftWaterMob;
import org.bukkit.craftbukkit.entity.CraftWeather;
import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.craftbukkit.entity.CraftZombie;

/**
 * Canonical behaviour for CraftEntity wrapper-type dispatch and factory selection.
 */
public final class EntityWrapperFactoryBehaviour {
    private static final EntityWrapperFactoryBehaviour INSTANCE = new EntityWrapperFactoryBehaviour();

    private EntityWrapperFactoryBehaviour() {
    }

    public static EntityWrapperFactoryBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftEntity createEntityWrapper(CraftServer server, Entity entity, PlayerResolver playerResolver) {
        if (entity instanceof EntityLiving) {
            if (entity instanceof EntityHuman) {
                if (entity instanceof EntityPlayer) {
                    return playerResolver.resolvePlayer((EntityPlayer) entity);
                }
                return new CraftHumanEntity(server, (EntityHuman) entity);
            } else if (entity instanceof EntityCreature) {
                if (entity instanceof EntityAnimal) {
                    if (entity instanceof EntityChicken) {
                        return new CraftChicken(server, (EntityChicken) entity);
                    } else if (entity instanceof EntityCow) {
                        return new CraftCow(server, (EntityCow) entity);
                    } else if (entity instanceof EntityPig) {
                        return new CraftPig(server, (EntityPig) entity);
                    } else if (entity instanceof EntityWolf) {
                        return new CraftWolf(server, (EntityWolf) entity);
                    } else if (entity instanceof EntitySheep) {
                        return new CraftSheep(server, (EntitySheep) entity);
                    } else {
                        return new CraftAnimals(server, (EntityAnimal) entity);
                    }
                } else if (entity instanceof EntityMonster) {
                    if (entity instanceof EntityZombie) {
                        if (entity instanceof EntityPigZombie) {
                            return new CraftPigZombie(server, (EntityPigZombie) entity);
                        }
                        return new CraftZombie(server, (EntityZombie) entity);
                    } else if (entity instanceof EntityCreeper) {
                        return new CraftCreeper(server, (EntityCreeper) entity);
                    } else if (entity instanceof EntityGiantZombie) {
                        return new CraftGiant(server, (EntityGiantZombie) entity);
                    } else if (entity instanceof EntitySkeleton) {
                        return new CraftSkeleton(server, (EntitySkeleton) entity);
                    } else if (entity instanceof EntitySpider) {
                        return new CraftSpider(server, (EntitySpider) entity);
                    } else {
                        return new CraftMonster(server, (EntityMonster) entity);
                    }
                } else if (entity instanceof EntityWaterAnimal) {
                    if (entity instanceof EntitySquid) {
                        return new CraftSquid(server, (EntitySquid) entity);
                    }
                    return new CraftWaterMob(server, (EntityWaterAnimal) entity);
                } else {
                    return new CraftCreature(server, (EntityCreature) entity);
                }
            } else if (entity instanceof EntitySlime) {
                return new CraftSlime(server, (EntitySlime) entity);
            } else if (entity instanceof EntityFlying) {
                if (entity instanceof EntityGhast) {
                    return new CraftGhast(server, (EntityGhast) entity);
                }
                return new CraftFlying(server, (EntityFlying) entity);
            } else {
                return new CraftLivingEntity(server, (EntityLiving) entity);
            }
        } else if (entity instanceof EntityArrow) {
            return new CraftArrow(server, (EntityArrow) entity);
        } else if (entity instanceof EntityBoat) {
            return new CraftBoat(server, (EntityBoat) entity);
        } else if (entity instanceof EntityEgg) {
            return new CraftEgg(server, (EntityEgg) entity);
        } else if (entity instanceof EntityFallingSand) {
            return new CraftFallingSand(server, (EntityFallingSand) entity);
        } else if (entity instanceof EntityFireball) {
            return new CraftFireball(server, (EntityFireball) entity);
        } else if (entity instanceof EntityFish) {
            return new CraftFish(server, (EntityFish) entity);
        } else if (entity instanceof EntityItem) {
            return new CraftItem(server, (EntityItem) entity);
        } else if (entity instanceof EntityWeather) {
            if (entity instanceof EntityWeatherStorm) {
                return new CraftLightningStrike(server, (EntityWeatherStorm) entity);
            }
            return new CraftWeather(server, (EntityWeather) entity);
        } else if (entity instanceof EntityMinecart) {
            EntityMinecart minecart = (EntityMinecart) entity;
            if (minecart.type == CraftMinecart.Type.StorageMinecart.getId()) {
                return new CraftStorageMinecart(server, minecart);
            } else if (minecart.type == CraftMinecart.Type.PoweredMinecart.getId()) {
                return new CraftPoweredMinecart(server, minecart);
            } else {
                return new CraftMinecart(server, minecart);
            }
        } else if (entity instanceof EntityPainting) {
            return new CraftPainting(server, (EntityPainting) entity);
        } else if (entity instanceof EntitySnowball) {
            return new CraftSnowball(server, (EntitySnowball) entity);
        } else if (entity instanceof EntityTNTPrimed) {
            return new CraftTNTPrimed(server, (EntityTNTPrimed) entity);
        } else {
            throw new IllegalArgumentException("Unknown entity");
        }
    }

    public interface PlayerResolver {
        CraftPlayer resolvePlayer(EntityPlayer entityPlayer);
    }
}
