package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyBlockAudioWrapperThinnessTest {
    private static final Path STEP_SOUND_PATH = Paths.get("src/main/java/net/minecraft/server/StepSound.java");

    @Test
    public void stepSoundDelegatesVolumeAndNameResolutionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(STEP_SOUND_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("StepSoundBehaviour"));
        Assert.assertTrue(text.contains("STEP_SOUND_BEHAVIOUR.getPrimaryVolume"));
        Assert.assertTrue(text.contains("STEP_SOUND_BEHAVIOUR.getSecondaryVolume"));
        Assert.assertTrue(text.contains("STEP_SOUND_BEHAVIOUR.getStepSoundName"));
        Assert.assertFalse(text.contains("return \"step.\" + this.a"));
    }
}
