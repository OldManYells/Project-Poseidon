package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat damage-event scaffold.
 */
public class EntityDamageEvent {
    private boolean cancelled;
    private int damage;

    public enum DamageCause {
        FIRE,
        LAVA,
        LIGHTNING,
        BLOCK_EXPLOSION,
        ENTITY_EXPLOSION,
        TNT_EXPLOSION,
        PLUGIN_EXPLOSION
    }

    public EntityDamageEvent() {
    }

    public EntityDamageEvent(Object damagee, DamageCause cause, int damage) {
        this.damage = damage;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
