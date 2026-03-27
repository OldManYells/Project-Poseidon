package com.legacyminecraft.compat.bukkit;


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
