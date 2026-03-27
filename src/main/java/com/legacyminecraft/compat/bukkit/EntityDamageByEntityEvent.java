package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat entity-damage event scaffold.
 */
public class EntityDamageByEntityEvent extends EntityDamageEvent {
    private boolean cancelled;
    private int damage;

    public EntityDamageByEntityEvent(Object damager, Object damagee, DamageCause cause, int damage) {
        this.damage = damage;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getDamage() {
        return damage;
    }
}
