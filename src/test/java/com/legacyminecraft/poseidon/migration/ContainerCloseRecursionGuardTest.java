package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContainerCloseRecursionGuardTest {
    private static final Path CONTAINER_PLAYER_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerPlayer.java");
    private static final Path CONTAINER_WORKBENCH_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerWorkbench.java");
    private static final Path PLAYER_CONTAINER_BEHAVIOUR_PATH = Paths.get("src/main/java/com/legacyminecraft/poseidon/inventory/PlayerContainerBehaviour.java");
    private static final Path WORKBENCH_CONTAINER_BEHAVIOUR_PATH = Paths.get("src/main/java/com/legacyminecraft/poseidon/inventory/WorkbenchContainerBehaviour.java");

    @Test
    public void legacyContainerCloseMethodsCallSuperBeforeDelegating() throws IOException {
        String playerContainerText = new String(Files.readAllBytes(CONTAINER_PLAYER_PATH), StandardCharsets.UTF_8);
        String workbenchContainerText = new String(Files.readAllBytes(CONTAINER_WORKBENCH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(playerContainerText.contains("super.a(entityhuman);"));
        Assert.assertTrue(playerContainerText.contains("PLAYER_CONTAINER_BEHAVIOUR.onClose"));
        Assert.assertTrue(workbenchContainerText.contains("super.a(entityhuman);"));
        Assert.assertTrue(workbenchContainerText.contains("WORKBENCH_CONTAINER_BEHAVIOUR.onClose"));
    }

    @Test
    public void canonicalCloseBehavioursDoNotReenterLegacyContainerCloseMethods() throws IOException {
        String playerBehaviourText = new String(Files.readAllBytes(PLAYER_CONTAINER_BEHAVIOUR_PATH), StandardCharsets.UTF_8);
        String workbenchBehaviourText = new String(Files.readAllBytes(WORKBENCH_CONTAINER_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertFalse(playerBehaviourText.contains("container.a(entityhuman)"));
        Assert.assertFalse(workbenchBehaviourText.contains("container.a(entityhuman)"));
    }
}
