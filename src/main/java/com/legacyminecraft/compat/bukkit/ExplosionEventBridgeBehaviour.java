package com.legacyminecraft.compat.bukkit;

import com.legacyminecraft.poseidon.world.ChunkPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Canonical Bukkit bridge behaviour for explosion events.
 */
public final class ExplosionEventBridgeBehaviour {
    private static final ExplosionEventBridgeBehaviour INSTANCE = new ExplosionEventBridgeBehaviour();

    private ExplosionEventBridgeBehaviour() {
    }

    public static ExplosionEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public EntityDamageByBlockEvent createBlockDamageEvent(Object damagee,
                                                           int damageDone,
                                                           Object source,
                                                           Object customDamageCause) {
        EntityDamageEvent.DamageCause resolvedCause = resolveDamageCause(customDamageCause);
        if (resolvedCause != null) {
            return new EntityDamageByBlockEvent(null, damagee, resolvedCause, damageDone);
        }
        if (isTntLikeSource(source)) {
            return new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.TNT_EXPLOSION, damageDone);
        }
        return new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
    }

    public DamageResolution resolveExplosionDamage(Object world,
                                                   Object source,
                                                   Object damagee,
                                                   int damageDone,
                                                   Object customDamageCause) {
        if (damagee != null && (source == null || isTntLikeSource(source))) {
            EntityDamageByBlockEvent event = createBlockDamageEvent(damagee, damageDone, source, customDamageCause);
            callEvent(world, event);
            return new DamageResolution(event.isCancelled(), event.getDamage());
        }

        EntityDamageByEntityEvent event =
                new EntityDamageByEntityEvent(source == null ? null : resolveBukkitEntity(source), damagee, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, damageDone);
        callEvent(world, event);
        return new DamageResolution(event.isCancelled(), event.getDamage());
    }

    private static EntityDamageEvent.DamageCause resolveDamageCause(Object customDamageCause) {
        if (customDamageCause instanceof EntityDamageEvent.DamageCause) {
            return (EntityDamageEvent.DamageCause) customDamageCause;
        }
        if (customDamageCause == null) {
            return null;
        }
        try {
            return EntityDamageEvent.DamageCause.valueOf(customDamageCause.toString());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    public ExplosionEventResult callExplosionEvent(Object world,
                                                   Object source,
                                                   double explosionX,
                                                   double explosionY,
                                                   double explosionZ,
                                                   List<ChunkPosition> mutableBlockPositions,
                                                   Set<ChunkPosition> canonicalBlocks) {
        Object bukkitWorld = resolveBukkitWorld(world);
        Object bukkitSource = source == null ? null : resolveBukkitEntity(source);
        Location location = new Location(bukkitWorld, explosionX, explosionY, explosionZ);

        List<Object> blockList = new ArrayList<Object>();
        for (int index = mutableBlockPositions.size() - 1; index >= 0; index--) {
            ChunkPosition position = mutableBlockPositions.get(index);
            if (position.y > 127 || position.y < 0) {
                mutableBlockPositions.remove(index);
                continue;
            }
            blockList.add(position);
        }

        EntityExplodeEvent event = new EntityExplodeEvent(bukkitSource, location, blockList);
        callEvent(world, event);

        if (event.isCancelled()) {
            return new ExplosionEventResult(true, event.getYield());
        }

        return new ExplosionEventResult(false, event.getYield());
    }

    private boolean isTntLikeSource(Object source) {
        if (source == null) {
            return false;
        }
        String simpleName = source.getClass().getSimpleName();
        return simpleName != null && simpleName.contains("TNT");
    }

    private static Object resolveBukkitEntity(Object source) {
        try {
            return source.getClass().getMethod("getBukkitEntity").invoke(source);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Object resolveBukkitWorld(Object world) {
        try {
            return world.getClass().getMethod("getWorld").invoke(world);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static void callEvent(Object world, Object event) {
        try {
            Object server = world.getClass().getMethod("getServer").invoke(world);
            Object pluginManager = server.getClass().getMethod("getPluginManager").invoke(server);
            pluginManager.getClass().getMethod("callEvent", Class.forName("org.bukkit.event.Event")).invoke(pluginManager, event);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public static final class ExplosionEventResult {
        private final boolean cancelled;
        private final float yield;

        public ExplosionEventResult(boolean cancelled, float yield) {
            this.cancelled = cancelled;
            this.yield = yield;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public float getYield() {
            return yield;
        }
    }

    public static final class DamageResolution {
        private final boolean cancelled;
        private final int damage;

        public DamageResolution(boolean cancelled, int damage) {
            this.cancelled = cancelled;
            this.damage = damage;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public int getDamage() {
            return damage;
        }
    }
}
