package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.ChunkPosition;
import net.minecraft.server.Entity;
import net.minecraft.server.World;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

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

    public EntityDamageByBlockEvent createBlockDamageEvent(org.bukkit.entity.Entity damagee,
                                                           int damageDone,
                                                           Entity source,
                                                           EntityDamageEvent.DamageCause customDamageCause) {
        if (customDamageCause != null) {
            return new EntityDamageByBlockEvent(null, damagee, customDamageCause, damageDone);
        }
        if (source instanceof net.minecraft.server.EntityTNTPrimed) {
            return new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.TNT_EXPLOSION, damageDone);
        }
        return new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
    }

    public DamageResolution resolveExplosionDamage(World world,
                                                   Entity source,
                                                   org.bukkit.entity.Entity damagee,
                                                   int damageDone,
                                                   EntityDamageEvent.DamageCause customDamageCause) {
        if (damagee != null && (source == null || source instanceof net.minecraft.server.EntityTNTPrimed)) {
            EntityDamageByBlockEvent event = createBlockDamageEvent(damagee, damageDone, source, customDamageCause);
            world.getServer().getPluginManager().callEvent(event);
            return new DamageResolution(event.isCancelled(), event.getDamage());
        }

        EntityDamageByEntityEvent event =
                new EntityDamageByEntityEvent(source.getBukkitEntity(), damagee, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, damageDone);
        world.getServer().getPluginManager().callEvent(event);
        return new DamageResolution(event.isCancelled(), event.getDamage());
    }

    public ExplosionEventResult callExplosionEvent(World world,
                                                   Entity source,
                                                   double explosionX,
                                                   double explosionY,
                                                   double explosionZ,
                                                   List<ChunkPosition> mutableBlockPositions,
                                                   Set<ChunkPosition> canonicalBlocks) {
        org.bukkit.World bukkitWorld = world.getWorld();
        org.bukkit.entity.Entity bukkitSource = source == null ? null : source.getBukkitEntity();
        Location location = new Location(bukkitWorld, explosionX, explosionY, explosionZ);

        List<org.bukkit.block.Block> blockList = new ArrayList<org.bukkit.block.Block>();
        for (int index = mutableBlockPositions.size() - 1; index >= 0; index--) {
            ChunkPosition position = mutableBlockPositions.get(index);
            if (position.y > 127 || position.y < 0) {
                mutableBlockPositions.remove(index);
                continue;
            }

            org.bukkit.block.Block block = bukkitWorld.getBlockAt(position.x, position.y, position.z);
            if (block.getType() != org.bukkit.Material.AIR) {
                blockList.add(block);
            }
        }

        EntityExplodeEvent event = new EntityExplodeEvent(bukkitSource, location, blockList);
        world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return new ExplosionEventResult(true, event.getYield());
        }

        mutableBlockPositions.clear();
        canonicalBlocks.clear();
        for (org.bukkit.block.Block affectedBlock : event.blockList()) {
            ChunkPosition coordinates = new ChunkPosition(affectedBlock.getX(), affectedBlock.getY(), affectedBlock.getZ());
            mutableBlockPositions.add(coordinates);
            canonicalBlocks.add(coordinates);
        }

        return new ExplosionEventResult(false, event.getYield());
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
