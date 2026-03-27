package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local wolf scaffold.
 */
public class EntityWolf extends EntityAnimal {
    private boolean angry;
    private boolean sitting;
    private boolean tamed;
    private String ownerName = "";

    public EntityWolf() {
    }

    public EntityWolf(World world) {
        super(world);
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

    public boolean ad() {
        return false;
    }

    public void poseidonClearMovementIntent() {
    }

    public void poseidonShowTameEffect(boolean success) {
    }

    public void poseidonSyncHealthWatcher(int healthWatcherIndex) {
    }
}
