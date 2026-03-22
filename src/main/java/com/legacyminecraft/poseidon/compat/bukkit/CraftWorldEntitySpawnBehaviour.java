package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLightningStrike;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.Vector;

/**
 * Canonical behavior for CraftWorld entity/projectile/lightning spawn orchestration.
 */
public final class CraftWorldEntitySpawnBehaviour {
    private static final CraftWorldEntitySpawnBehaviour INSTANCE = new CraftWorldEntitySpawnBehaviour();

    private CraftWorldEntitySpawnBehaviour() {
    }

    public static CraftWorldEntitySpawnBehaviour getInstance() {
        return INSTANCE;
    }

    public Arrow spawnArrow(WorldServer worldServer, Location spawnLocation, Vector velocity, float speed, float spread) {
        EntityArrow arrow = new EntityArrow(worldServer);
        arrow.setPositionRotation(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), 0, 0);
        worldServer.addEntity(arrow);
        arrow.a(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
        return (Arrow) arrow.getBukkitEntity();
    }

    public LivingEntity spawnCreature(WorldServer worldServer, CraftServer craftServer, Location spawnLocation, CreatureType creatureType) {
        try {
            EntityLiving entityCreature = (EntityLiving) EntityTypes.a(creatureType.getName(), worldServer);
            entityCreature.setPosition(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
            LivingEntity livingEntity = (LivingEntity) CraftEntity.getEntity(craftServer, entityCreature);
            worldServer.addEntity(entityCreature, CreatureSpawnEvent.SpawnReason.CUSTOM);
            return livingEntity;
        } catch (Exception exception) {
            return null;
        }
    }

    public LightningStrike strikeLightning(WorldServer worldServer, CraftServer craftServer, Location strikeLocation, boolean effectOnly) {
        EntityWeatherStorm lightning = new EntityWeatherStorm(
                worldServer,
                strikeLocation.getX(),
                strikeLocation.getY(),
                strikeLocation.getZ(),
                effectOnly
        );
        worldServer.strikeLightning(lightning);
        return new CraftLightningStrike(craftServer, lightning);
    }
}
