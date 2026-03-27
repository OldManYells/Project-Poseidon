package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerReloadWorkerWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_RELOAD_WORKER_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerReloadWorkerBehaviour.java");

    @Test
    public void craftServerDelegatesReloadWorkerDrainWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_RELOAD_WORKER_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void reload() {", "private void loadCustomPermissions() {");

        Assert.assertTrue(craftServerText.contains("CraftServerReloadWorkerBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RELOAD_WORKER_BEHAVIOUR.drainAndWarn(getScheduler(), getLogger());"));

        Assert.assertFalse(section.contains("while (pollCount < 50 && getScheduler().getActiveWorkers().size() > 0)"));
        Assert.assertFalse(section.contains("Thread.sleep(50);"));
        Assert.assertFalse(section.contains("List<BukkitWorker> overdueWorkers = getScheduler().getActiveWorkers();"));
        Assert.assertFalse(section.contains("Nag author: '%s' of '%s' about the following: %s"));

        Assert.assertTrue(behaviourText.contains("drainAndWarn(BukkitScheduler scheduler, Logger logger)"));
        Assert.assertTrue(behaviourText.contains("while (pollCount < 50 && scheduler.getActiveWorkers().size() > 0)"));
        Assert.assertTrue(behaviourText.contains("Thread.sleep(50);"));
        Assert.assertTrue(behaviourText.contains("List<BukkitWorker> overdueWorkers = scheduler.getActiveWorkers();"));
        Assert.assertTrue(behaviourText.contains("Nag author: '%s' of '%s' about the following: %s"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
