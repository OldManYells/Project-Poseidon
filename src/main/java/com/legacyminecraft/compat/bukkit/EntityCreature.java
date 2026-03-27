package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat creature entity scaffold.
 */
public class EntityCreature extends EntityLiving implements Creature {
    public EntityLiving target;
    public PathEntity pathEntity;

    public void setPathEntity(PathEntity pathEntity) {
        this.pathEntity = pathEntity;
    }
}
