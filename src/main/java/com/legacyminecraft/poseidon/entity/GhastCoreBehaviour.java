package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Item;

public final class GhastCoreBehaviour {
    private static final GhastCoreBehaviour INSTANCE = new GhastCoreBehaviour();

    private GhastCoreBehaviour() {
    }

    public static GhastCoreBehaviour getInstance() {
        return INSTANCE;
    }

    public byte createInitialAttackWatcher() {
        return (byte) 0;
    }

    public String resolveTexture(byte attackWatcher) {
        return attackWatcher == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
    }

    public boolean shouldDespawnWhenMonstersDisabled(boolean worldStatic, int spawnMonstersFlag) {
        return !worldStatic && spawnMonstersFlag == 0;
    }

    public String getAmbientSound() {
        return "mob.ghast.moan";
    }

    public String getHurtSound() {
        return "mob.ghast.scream";
    }

    public String getDeathSound() {
        return "mob.ghast.death";
    }

    public int getDropItemId() {
        return Item.SULPHUR.id;
    }

    public float getSoundVolume() {
        return 10.0F;
    }

    public boolean canSpawn(int randomRoll20, boolean superCanSpawn, int spawnMonstersFlag) {
        return randomRoll20 == 0 && superCanSpawn && spawnMonstersFlag > 0;
    }

    public int getMaxClusterSize() {
        return 1;
    }
}
