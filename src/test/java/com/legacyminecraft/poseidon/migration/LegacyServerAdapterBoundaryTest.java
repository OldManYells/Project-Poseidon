package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyServerAdapterBoundaryTest {
    private static final Path SERVER_COMMAND_PATH = Paths.get("src/main/java/net/minecraft/server/ServerCommand.java");
    private static final Path SERVER_GUI_FOCUS_ADAPTER_PATH = Paths.get("src/main/java/net/minecraft/server/ServerGuiFocusAdapter.java");
    private static final Path STEP_SOUND_SAND_PATH = Paths.get("src/main/java/net/minecraft/server/StepSoundSand.java");
    private static final Path STEP_SOUND_STONE_PATH = Paths.get("src/main/java/net/minecraft/server/StepSoundStone.java");

    @Test
    public void serverAdaptersDelegateToCanonicalBridgeBehaviours() throws IOException {
        String serverCommandText = new String(Files.readAllBytes(SERVER_COMMAND_PATH), StandardCharsets.UTF_8);
        String serverGuiFocusAdapterText = new String(Files.readAllBytes(SERVER_GUI_FOCUS_ADAPTER_PATH), StandardCharsets.UTF_8);
        String stepSoundSandText = new String(Files.readAllBytes(STEP_SOUND_SAND_PATH), StandardCharsets.UTF_8);
        String stepSoundStoneText = new String(Files.readAllBytes(STEP_SOUND_STONE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(serverCommandText.contains("import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;"));
        Assert.assertTrue(serverCommandText.contains("SERVER_COMMAND_ENVELOPE_BEHAVIOUR.createState(s, icommandlistener)"));
        Assert.assertFalse(serverCommandText.contains("this.command = s;"));

        Assert.assertTrue(serverGuiFocusAdapterText.contains("import com.legacyminecraft.poseidon.runtime.gui.ServerGuiFocusBridgeBehaviour;"));
        Assert.assertTrue(serverGuiFocusAdapterText.contains("SERVER_GUI_FOCUS_BRIDGE_BEHAVIOUR.onFocusGained(focusevent);"));
        Assert.assertFalse(serverGuiFocusAdapterText.contains("public void focusGained(FocusEvent focusevent) {}"));

        Assert.assertTrue(stepSoundSandText.contains("import com.legacyminecraft.poseidon.block.StepSoundVariantContract;"));
        Assert.assertTrue(stepSoundSandText.contains("final class StepSoundSand extends StepSound implements StepSoundVariantContract"));

        Assert.assertTrue(stepSoundStoneText.contains("import com.legacyminecraft.poseidon.block.StepSoundVariantContract;"));
        Assert.assertTrue(stepSoundStoneText.contains("final class StepSoundStone extends StepSound implements StepSoundVariantContract"));
    }
}
