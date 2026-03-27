package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat creeper-power event scaffold.
 */
public class CreeperPowerEvent extends Event {
    private final Entity entity;
    private final Entity lightning;
    private final PowerCause cause;

    public CreeperPowerEvent(Entity entity, PowerCause cause) {
        this(entity, null, cause);
    }

    public CreeperPowerEvent(Entity entity, Entity lightning, PowerCause cause) {
        this.entity = entity;
        this.lightning = lightning;
        this.cause = cause;
    }

    public Entity getEntity() {
        return entity;
    }

    public Entity getLightning() {
        return lightning;
    }

    public PowerCause getCause() {
        return cause;
    }

    public enum PowerCause {
        LIGHTNING,
        SET_ON,
        SET_OFF
    }
}
