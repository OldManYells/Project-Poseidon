package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityWeatherStorm;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Canonical Bukkit bridge behaviour for entity lightning-strike events.
 */
public final class EntityLightningStrikeBridgeBehaviour {
    private static final EntityLightningStrikeBridgeBehaviour INSTANCE = new EntityLightningStrikeBridgeBehaviour();

    private EntityLightningStrikeBridgeBehaviour() {
    }

    public static EntityLightningStrikeBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public LightningStrikeResult handleStrike(Entity entity, EntityWeatherStorm stormEntity, int currentFireTicks, int igniteFireTicks) {
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(
                stormEntity.getBukkitEntity(),
                entity.getBukkitEntity(),
                EntityDamageEvent.DamageCause.LIGHTNING,
                5
        );
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return LightningStrikeResult.cancelled(currentFireTicks);
        }

        int updatedFireTicks = currentFireTicks + 1;
        if (updatedFireTicks == 0) {
            updatedFireTicks = igniteFireTicks;
        }

        return LightningStrikeResult.applied(event.getDamage(), updatedFireTicks);
    }

    public static final class LightningStrikeResult {
        public final boolean cancelled;
        public final int damage;
        public final int updatedFireTicks;

        private LightningStrikeResult(boolean cancelled, int damage, int updatedFireTicks) {
            this.cancelled = cancelled;
            this.damage = damage;
            this.updatedFireTicks = updatedFireTicks;
        }

        public static LightningStrikeResult cancelled(int currentFireTicks) {
            return new LightningStrikeResult(true, 0, currentFireTicks);
        }

        public static LightningStrikeResult applied(int damage, int updatedFireTicks) {
            return new LightningStrikeResult(false, damage, updatedFireTicks);
        }
    }
}
