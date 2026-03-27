package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local pig-zombie scaffold.
 */
public class EntityPigZombie extends EntityZombie {
    public int angerLevel;
    private int soundDelay;

    public EntityPigZombie() {
    }

    public EntityPigZombie(World world) {
        super(world);
    }

    public void poseidonSetSoundDelay(int soundDelay) {
        this.soundDelay = soundDelay;
    }

    public int poseidonGetSoundDelay() {
        return soundDelay;
    }
}
