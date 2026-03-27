package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat wolf entity scaffold.
 */
public class EntityWolf extends EntityAnimal implements Wolf {
    private boolean angry;
    private boolean sitting;
    private boolean tamed;
    private String ownerName = "";
    public PathEntity pathEntity;

    public EntityWolf() {
    }

    public EntityWolf(WorldServer world) {
        this.world = world;
    }

    public boolean isAngry() {
        return angry;
    }

    public void setAngry(boolean angry) {
        this.angry = angry;
    }

    public boolean isSitting() {
        return sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public boolean isTamed() {
        return tamed;
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? "" : ownerName;
    }

    public void setPathEntity(PathEntity pathEntity) {
        this.pathEntity = pathEntity;
    }
}
