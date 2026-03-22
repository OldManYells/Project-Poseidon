package com.legacyminecraft.poseidon.block;

public final class StepSoundBehaviour {
    private static final StepSoundBehaviour INSTANCE = new StepSoundBehaviour();

    private StepSoundBehaviour() {
    }

    public static StepSoundBehaviour getInstance() {
        return INSTANCE;
    }

    public float getPrimaryVolume(float primaryVolume) {
        return primaryVolume;
    }

    public float getSecondaryVolume(float secondaryVolume) {
        return secondaryVolume;
    }

    public String getStepSoundName(String key) {
        return "step." + key;
    }
}
