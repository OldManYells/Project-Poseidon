package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.Item;
import org.bukkit.event.entity.CreeperPowerEvent;

import java.util.Random;

public final class CreeperPowerBehaviour {
    private static final CreeperPowerBehaviour INSTANCE = new CreeperPowerBehaviour();

    private CreeperPowerBehaviour() {
    }

    public static CreeperPowerBehaviour getInstance() {
        return INSTANCE;
    }

    public byte createInitialFuseDirectionWatcherValue() {
        return (byte) -1;
    }

    public byte createInitialPoweredWatcherValue() {
        return (byte) 0;
    }

    public boolean shouldPersistPoweredTag(boolean powered) {
        return powered;
    }

    public boolean readPoweredFlag(boolean poweredTagValue) {
        return poweredTagValue;
    }

    public String getAmbientSound() {
        return "mob.creeper";
    }

    public String getDeathSound() {
        return "mob.creeperdeath";
    }

    public void dropMusicDiscIfKilledBySkeleton(EntityCreeper creeper, Entity killer, Random random) {
        if (!(killer instanceof EntityArrow)) {
            return;
        }

        EntityLiving shooter = ((EntityArrow) killer).shooter;
        if (shooter instanceof EntitySkeleton) {
            creeper.b(Item.GOLD_RECORD.id + random.nextInt(2), 1);
        }
    }

    public int getDropItemId() {
        return Item.SULPHUR.id;
    }

    public void onLightningStrike(EntityCreeper creeper, EntityWeatherStorm lightning) {
        CreeperPowerEvent event = new CreeperPowerEvent(creeper.getBukkitEntity(), lightning.getBukkitEntity(), CreeperPowerEvent.PowerCause.LIGHTNING);
        creeper.world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        creeper.setPowered(true);
    }

    public byte resolvePoweredWatcherValue(boolean powered) {
        return powered ? (byte) 1 : (byte) 0;
    }
}
