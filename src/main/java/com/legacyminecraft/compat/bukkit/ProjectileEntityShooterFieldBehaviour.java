package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for direct projectile shooter/owner field access in NMS entities.
 */
public final class ProjectileEntityShooterFieldBehaviour {
    private static final ProjectileEntityShooterFieldBehaviour INSTANCE = new ProjectileEntityShooterFieldBehaviour();

    private ProjectileEntityShooterFieldBehaviour() {
    }

    public static ProjectileEntityShooterFieldBehaviour getInstance() {
        return INSTANCE;
    }

    public net.minecraft.server.EntityLiving getArrowShooter(net.minecraft.server.EntityArrow arrow) {
        return arrow.shooter;
    }

    public void setArrowShooter(net.minecraft.server.EntityArrow arrow, net.minecraft.server.EntityLiving shooter) {
        arrow.shooter = shooter;
    }

    public net.minecraft.server.EntityLiving getEggThrower(net.minecraft.server.EntityEgg egg) {
        return egg.thrower;
    }

    public void setEggThrower(net.minecraft.server.EntityEgg egg, net.minecraft.server.EntityLiving thrower) {
        egg.thrower = thrower;
    }

    public net.minecraft.server.EntityLiving getSnowballShooter(net.minecraft.server.EntitySnowball snowball) {
        return snowball.shooter;
    }

    public void setSnowballShooter(net.minecraft.server.EntitySnowball snowball, net.minecraft.server.EntityLiving shooter) {
        snowball.shooter = shooter;
    }

    public net.minecraft.server.EntityLiving getFireballShooter(net.minecraft.server.EntityFireball fireball) {
        return fireball.shooter;
    }

    public void setFireballShooter(net.minecraft.server.EntityFireball fireball, net.minecraft.server.EntityLiving shooter) {
        fireball.shooter = shooter;
    }

    public net.minecraft.server.EntityHuman getFishOwner(net.minecraft.server.EntityFish fish) {
        return fish.owner;
    }

    public void setFishOwner(net.minecraft.server.EntityFish fish, net.minecraft.server.EntityHuman owner) {
        fish.owner = owner;
    }
}
