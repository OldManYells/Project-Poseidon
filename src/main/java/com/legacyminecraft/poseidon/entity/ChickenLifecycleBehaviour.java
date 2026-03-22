package com.legacyminecraft.poseidon.entity;

import java.util.Random;

public final class ChickenLifecycleBehaviour {
    private static final ChickenLifecycleBehaviour INSTANCE = new ChickenLifecycleBehaviour();

    private static final int EGG_LAY_MIN_TICKS = 6000;
    private static final int EGG_LAY_RANDOM_TICKS = 6000;

    private ChickenLifecycleBehaviour() {
    }

    public static ChickenLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public int createInitialEggLayTimer(Random random) {
        return random.nextInt(EGG_LAY_RANDOM_TICKS) + EGG_LAY_MIN_TICKS;
    }

    public ChickenTickState tick(ChickenTickState state) {
        float previousWingRotation = state.wingRotation;
        float previousFlapSpeed = state.flapSpeed;

        float updatedFlapSpeed = (float) ((double) state.flapSpeed + (double) (state.onGround ? -1 : 4) * 0.3D);
        if (updatedFlapSpeed < 0.0F) {
            updatedFlapSpeed = 0.0F;
        }

        if (updatedFlapSpeed > 1.0F) {
            updatedFlapSpeed = 1.0F;
        }

        float updatedWingMotionDamping = state.wingMotionDamping;
        if (!state.onGround && updatedWingMotionDamping < 1.0F) {
            updatedWingMotionDamping = 1.0F;
        }

        updatedWingMotionDamping = (float) ((double) updatedWingMotionDamping * 0.9D);

        double updatedVerticalMotion = state.verticalMotion;
        if (!state.onGround && updatedVerticalMotion < 0.0D) {
            updatedVerticalMotion *= 0.6D;
        }

        float updatedWingRotation = state.wingRotation + updatedWingMotionDamping * 2.0F;

        int updatedEggLayTimer = state.eggLayTimer - 1;
        boolean shouldLayEgg = !state.worldStatic && updatedEggLayTimer <= 0;
        if (shouldLayEgg) {
            updatedEggLayTimer = createInitialEggLayTimer(state.random);
        }

        return new ChickenTickState(
                updatedWingRotation,
                updatedFlapSpeed,
                previousFlapSpeed,
                previousWingRotation,
                updatedWingMotionDamping,
                updatedVerticalMotion,
                state.onGround,
                state.worldStatic,
                updatedEggLayTimer,
                state.random,
                shouldLayEgg
        );
    }

    public String getAmbientSound() {
        return "mob.chicken";
    }

    public String getHurtSound() {
        return "mob.chickenhurt";
    }

    public String getDeathSound() {
        return "mob.chickenhurt";
    }

    public int getDropItemId() {
        return net.minecraft.server.Item.FEATHER.id;
    }

    public static final class ChickenTickState {
        public final float wingRotation;
        public final float flapSpeed;
        public final float previousFlapSpeed;
        public final float previousWingRotation;
        public final float wingMotionDamping;
        public final double verticalMotion;
        public final boolean onGround;
        public final boolean worldStatic;
        public final int eggLayTimer;
        public final Random random;
        public final boolean shouldLayEgg;

        public ChickenTickState(float wingRotation, float flapSpeed, float previousFlapSpeed, float previousWingRotation, float wingMotionDamping, double verticalMotion, boolean onGround, boolean worldStatic, int eggLayTimer, Random random) {
            this(wingRotation, flapSpeed, previousFlapSpeed, previousWingRotation, wingMotionDamping, verticalMotion, onGround, worldStatic, eggLayTimer, random, false);
        }

        public ChickenTickState(float wingRotation, float flapSpeed, float previousFlapSpeed, float previousWingRotation, float wingMotionDamping, double verticalMotion, boolean onGround, boolean worldStatic, int eggLayTimer, Random random, boolean shouldLayEgg) {
            this.wingRotation = wingRotation;
            this.flapSpeed = flapSpeed;
            this.previousFlapSpeed = previousFlapSpeed;
            this.previousWingRotation = previousWingRotation;
            this.wingMotionDamping = wingMotionDamping;
            this.verticalMotion = verticalMotion;
            this.onGround = onGround;
            this.worldStatic = worldStatic;
            this.eggLayTimer = eggLayTimer;
            this.random = random;
            this.shouldLayEgg = shouldLayEgg;
        }
    }
}
