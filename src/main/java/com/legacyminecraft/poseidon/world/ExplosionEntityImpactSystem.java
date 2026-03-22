package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.compat.bukkit.ExplosionEventBridgeBehaviour;
import net.minecraft.server.Entity;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

/**
 * Canonical orchestration for applying explosion impact and knockback to nearby entities.
 */
public final class ExplosionEntityImpactSystem {
    private static final ExplosionEntityImpactSystem INSTANCE = new ExplosionEntityImpactSystem();
    private static final ExplosionDensityCacheBehaviour EXPLOSION_DENSITY_CACHE_BEHAVIOUR = ExplosionDensityCacheBehaviour.getInstance();
    private static final ExplosionEffectBehaviour EXPLOSION_EFFECT_BEHAVIOUR = ExplosionEffectBehaviour.getInstance();
    private static final ExplosionEventBridgeBehaviour EXPLOSION_EVENT_BRIDGE_BEHAVIOUR = ExplosionEventBridgeBehaviour.getInstance();
    private static final ExplosionImpactMathBehaviour EXPLOSION_IMPACT_MATH_BEHAVIOUR = ExplosionImpactMathBehaviour.getInstance();

    private ExplosionEntityImpactSystem() {
    }

    public static ExplosionEntityImpactSystem getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("rawtypes")
    public void applyImpacts(
            World world,
            Entity source,
            float size,
            double posX,
            double posY,
            double posZ,
            Vec3D explosionCenter,
            List nearbyEntities,
            boolean optimizeExplosions,
            boolean sendMotion,
            EntityDamageEvent.DamageCause customDamageCause
    ) {
        if (nearbyEntities == null || nearbyEntities.isEmpty()) {
            return;
        }

        for (int index = 0; index < nearbyEntities.size(); ++index) {
            Entity entity = (Entity) nearbyEntities.get(index);
            double blockDensity;
            if (optimizeExplosions) {
                blockDensity = EXPLOSION_DENSITY_CACHE_BEHAVIOUR.getOrComputeDensity(
                        world,
                        explosionCenter,
                        posX,
                        posY,
                        posZ,
                        entity.boundingBox
                );
            } else {
                blockDensity = world.a(explosionCenter, entity.boundingBox);
            }

            ExplosionImpactMathBehaviour.ImpactComputation impact =
                    EXPLOSION_IMPACT_MATH_BEHAVIOUR.computeImpact(entity, posX, posY, posZ, size, blockDensity);
            if (impact == null) {
                continue;
            }

            org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
            int damageDone = EXPLOSION_EFFECT_BEHAVIOUR.computeEntityDamage(size, impact.getImpactScale());
            ExplosionEventBridgeBehaviour.DamageResolution damageResolution =
                    EXPLOSION_EVENT_BRIDGE_BEHAVIOUR.resolveExplosionDamage(
                            world,
                            source,
                            damagee,
                            damageDone,
                            customDamageCause
                    );

            if (!damageResolution.isCancelled()) {
                entity.damageEntity(source, damageResolution.getDamage());
                EXPLOSION_IMPACT_MATH_BEHAVIOUR.applyKnockback(entity, impact, sendMotion);
            }
        }
    }
}
