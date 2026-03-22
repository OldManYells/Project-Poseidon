package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Canonical Bukkit bridge behaviour for entity fire-damage events.
 */
public final class EntityFireDamageBridgeBehaviour {
    private static final EntityFireDamageBridgeBehaviour INSTANCE = new EntityFireDamageBridgeBehaviour();

    private EntityFireDamageBridgeBehaviour() {
    }

    public static EntityFireDamageBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public FireDamageResult resolveFireDamage(Entity entity, int initialDamage) {
        if (entity instanceof EntityLiving) {
            EntityDamageEvent event = new EntityDamageEvent(entity.getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE, initialDamage);
            entity.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return FireDamageResult.cancelled();
            }

            return FireDamageResult.applied(event.getDamage());
        }

        return FireDamageResult.applied(initialDamage);
    }

    public static final class FireDamageResult {
        public final boolean cancelled;
        public final int damage;

        private FireDamageResult(boolean cancelled, int damage) {
            this.cancelled = cancelled;
            this.damage = damage;
        }

        public static FireDamageResult cancelled() {
            return new FireDamageResult(true, 0);
        }

        public static FireDamageResult applied(int damage) {
            return new FireDamageResult(false, damage);
        }
    }
}
