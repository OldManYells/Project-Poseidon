package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerReloadOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_RELOAD_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerReloadOrchestrationBehaviour.java");

    @Test
    public void craftServerDelegatesReloadOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_RELOAD_ORCHESTRATION_BEHAVIOUR_PATH);

        Assert.assertTrue(craftServerText.contains("CraftServerReloadOrchestrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RELOAD_ORCHESTRATION_BEHAVIOUR.reload(new CraftServerReloadOrchestrationBehaviour.ReloadActions()"));
        Assert.assertTrue(craftServerText.contains("public PropertyManager createAndApplyPropertyManager()"));
        Assert.assertTrue(craftServerText.contains("public void applySettings(PropertyManager config)"));
        Assert.assertTrue(craftServerText.contains("public void cleanup()"));
        Assert.assertTrue(craftServerText.contains("public void drainAndWarn()"));
        Assert.assertTrue(craftServerText.contains("public void bootstrap()"));

        Assert.assertTrue(behaviourText.contains("reload(ReloadActions actions)"));
        Assert.assertTrue(behaviourText.contains("actions.loadConfig();"));
        Assert.assertTrue(behaviourText.contains("PropertyManager config = actions.createAndApplyPropertyManager();"));
        Assert.assertTrue(behaviourText.contains("actions.applySettings(config);"));
        Assert.assertTrue(behaviourText.contains("actions.cleanup();"));
        Assert.assertTrue(behaviourText.contains("actions.drainAndWarn();"));
        Assert.assertTrue(behaviourText.contains("actions.bootstrap();"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
