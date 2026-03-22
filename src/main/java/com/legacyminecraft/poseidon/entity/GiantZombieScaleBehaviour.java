package com.legacyminecraft.poseidon.entity;

public final class GiantZombieScaleBehaviour {
    private static final GiantZombieScaleBehaviour INSTANCE = new GiantZombieScaleBehaviour();

    private GiantZombieScaleBehaviour() {
    }

    public static GiantZombieScaleBehaviour getInstance() {
        return INSTANCE;
    }

    public int getAttackDamage() {
        return 50;
    }

    public int scaleHealth(int baseHealth) {
        return baseHealth * 10;
    }

    public float scaleEyeHeight(float baseEyeHeight) {
        return baseEyeHeight * 6.0F;
    }

    public float scaleWidth(float baseWidth) {
        return baseWidth * 6.0F;
    }

    public float scaleLength(float baseLength) {
        return baseLength * 6.0F;
    }

    public float resolvePathWeight(float worldLight) {
        return worldLight - 0.5F;
    }
}
