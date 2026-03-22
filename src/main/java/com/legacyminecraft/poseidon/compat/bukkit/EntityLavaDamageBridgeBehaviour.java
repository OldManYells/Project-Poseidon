package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Canonical Bukkit bridge behaviour for entity lava-contact damage and ignition events.
 */
public final class EntityLavaDamageBridgeBehaviour {
    private static final EntityLavaDamageBridgeBehaviour INSTANCE = new EntityLavaDamageBridgeBehaviour();

    private EntityLavaDamageBridgeBehaviour() {
    }

    public static EntityLavaDamageBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public LavaContactResult resolveLavaContact(Entity entity, int currentFireTicks) {
        if (!(entity instanceof EntityLiving)) {
            return LavaContactResult.applied(4, 600);
        }

        org.bukkit.Server server = entity.world.getServer();
        org.bukkit.block.Block damager = null;
        org.bukkit.entity.Entity damagee = entity.getBukkitEntity();

        EntityDamageByBlockEvent damageEvent = new EntityDamageByBlockEvent(
                damager,
                damagee,
                EntityDamageEvent.DamageCause.LAVA,
                4
        );
        server.getPluginManager().callEvent(damageEvent);

        boolean applyDamage = !damageEvent.isCancelled();
        int damage = applyDamage ? damageEvent.getDamage() : 0;
        int updatedFireTicks = currentFireTicks;

        if (currentFireTicks <= 0) {
            EntityCombustEvent combustEvent = new EntityCombustEvent(damagee);
            server.getPluginManager().callEvent(combustEvent);
            if (!combustEvent.isCancelled()) {
                updatedFireTicks = 600;
            }
        } else {
            updatedFireTicks = 600;
        }

        return new LavaContactResult(applyDamage, damage, updatedFireTicks);
    }

    public static final class LavaContactResult {
        public final boolean applyDamage;
        public final int damage;
        public final int updatedFireTicks;

        private LavaContactResult(boolean applyDamage, int damage, int updatedFireTicks) {
            this.applyDamage = applyDamage;
            this.damage = damage;
            this.updatedFireTicks = updatedFireTicks;
        }

        public static LavaContactResult applied(int damage, int updatedFireTicks) {
            return new LavaContactResult(true, damage, updatedFireTicks);
        }
    }
}
