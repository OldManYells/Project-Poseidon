package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat block-damage event scaffold.
 */
public class EntityDamageByBlockEvent extends EntityDamageEvent {
    private boolean cancelled;
    private int damage;

    public EntityDamageByBlockEvent(Object damager, Object damagee, DamageCause cause, int damage) {
        this.damage = damage;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getDamage() {
        return damage;
    }
}
