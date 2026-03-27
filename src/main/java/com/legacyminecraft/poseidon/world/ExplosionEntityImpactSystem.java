package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.compat.bukkit.EntityDamageEvent;
import com.legacyminecraft.compat.bukkit.ExplosionEventBridgeBehaviour;

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
            Object source,
            float size,
            double posX,
            double posY,
            double posZ,
            Object explosionCenter,
            List nearbyEntities,
            boolean optimizeExplosions,
            boolean sendMotion,
            Object customDamageCause
    ) {
        if (nearbyEntities == null || nearbyEntities.isEmpty()) {
            return;
        }

        for (int index = 0; index < nearbyEntities.size(); ++index) {
            Object entity = nearbyEntities.get(index);
            double blockDensity;
            if (optimizeExplosions) {
                blockDensity = EXPLOSION_DENSITY_CACHE_BEHAVIOUR.getOrComputeDensity(
                        world,
                        explosionCenter,
                        posX,
                        posY,
                        posZ,
                        readBoundingBox(entity)
                );
            } else {
                blockDensity = sampleBlockDensity(world, explosionCenter, readBoundingBox(entity));
            }

            ExplosionImpactMathBehaviour.ImpactComputation impact =
                    EXPLOSION_IMPACT_MATH_BEHAVIOUR.computeImpact(entity, posX, posY, posZ, size, blockDensity);
            if (impact == null) {
                continue;
            }

            Object damagee = resolveBukkitEntity(entity);
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
                damageEntity(entity, source, damageResolution.getDamage());
                EXPLOSION_IMPACT_MATH_BEHAVIOUR.applyKnockback(entity, impact, sendMotion);
            }
        }
    }

    private static AxisAlignedBB readBoundingBox(Object entity) {
        try {
            java.lang.reflect.Field field = entity.getClass().getField("boundingBox");
            Object value = field.get(entity);
            return value instanceof AxisAlignedBB ? (AxisAlignedBB) value : AxisAlignedBB.a(0, 0, 0, 0, 0, 0);
        } catch (ReflectiveOperationException ignored) {
            return AxisAlignedBB.a(0, 0, 0, 0, 0, 0);
        }
    }

    private static double sampleBlockDensity(World world, Object explosionCenter, AxisAlignedBB targetBounds) {
        if (explosionCenter instanceof Vec3D) {
            return world.a((Vec3D) explosionCenter, targetBounds);
        }
        try {
            java.lang.reflect.Method[] methods = world.getClass().getMethods();
            for (int index = 0; index < methods.length; index++) {
                java.lang.reflect.Method method = methods[index];
                if (method.getName().equals("a") && method.getParameterTypes().length == 2) {
                    Object value = method.invoke(world, explosionCenter, targetBounds);
                    return value instanceof Double ? ((Double) value).doubleValue() : 0.0D;
                }
            }
        } catch (ReflectiveOperationException ignored) {
        }
        return 0.0D;
    }

    private static void damageEntity(Object entity, Object source, int damage) {
        try {
            java.lang.reflect.Method[] methods = entity.getClass().getMethods();
            for (int index = 0; index < methods.length; index++) {
                java.lang.reflect.Method method = methods[index];
                if (method.getName().equals("damageEntity") && method.getParameterTypes().length == 2) {
                    method.invoke(entity, source, Integer.valueOf(damage));
                    return;
                }
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private static Object resolveBukkitEntity(Object entity) {
        if (entity == null) {
            return null;
        }
        try {
            return entity.getClass().getMethod("getBukkitEntity").invoke(entity);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
