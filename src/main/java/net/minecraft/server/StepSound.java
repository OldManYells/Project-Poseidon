package net.minecraft.server;

import com.legacyminecraft.poseidon.block.StepSoundBehaviour;

public class StepSound {
    private static final StepSoundBehaviour STEP_SOUND_BEHAVIOUR = StepSoundBehaviour.getInstance();

    public final String a;
    public final float b;
    public final float c;

    public StepSound(String s, float f, float f1) {
        this.a = s;
        this.b = f;
        this.c = f1;
    }

    public float getVolume1() {
        return STEP_SOUND_BEHAVIOUR.getPrimaryVolume(this.b);
    }

    public float getVolume2() {
        return STEP_SOUND_BEHAVIOUR.getSecondaryVolume(this.c);
    }

    public String getName() {
        return STEP_SOUND_BEHAVIOUR.getStepSoundName(this.a);
    }
}
