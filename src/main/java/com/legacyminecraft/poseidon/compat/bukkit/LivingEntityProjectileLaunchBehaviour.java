package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityEgg;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Snowball;

/**
 * Canonical behaviour for CraftLivingEntity projectile-launch bridge operations.
 */
public final class LivingEntityProjectileLaunchBehaviour {
    private static final LivingEntityProjectileLaunchBehaviour INSTANCE = new LivingEntityProjectileLaunchBehaviour();

    private LivingEntityProjectileLaunchBehaviour() {
    }

    public static LivingEntityProjectileLaunchBehaviour getInstance() {
        return INSTANCE;
    }

    public Egg throwEgg(World world, EntityLiving thrower) {
        EntityEgg egg = new EntityEgg(world, thrower);
        world.addEntity(egg);
        return (Egg) egg.getBukkitEntity();
    }

    public Snowball throwSnowball(World world, EntityLiving thrower) {
        EntitySnowball snowball = new EntitySnowball(world, thrower);
        world.addEntity(snowball);
        return (Snowball) snowball.getBukkitEntity();
    }

    public Arrow shootArrow(World world, EntityLiving shooter) {
        EntityArrow arrow = new EntityArrow(world, shooter);
        world.addEntity(arrow);
        return (Arrow) arrow.getBukkitEntity();
    }
}
