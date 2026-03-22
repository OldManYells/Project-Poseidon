package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalEntityTargetBridgeBoundaryTest {
    private static final Path MONSTER_CORE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/MonsterCoreBehaviour.java");
    private static final Path GHAST_AI_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/GhastAiBehaviour.java");
    private static final Path PIG_ZOMBIE_AGGRO_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/PigZombieAggroBehaviour.java");
    private static final Path SPIDER_COMBAT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/SpiderCombatBehaviour.java");
    private static final Path WOLF_COMBAT_INTERACTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/WolfCombatInteractionBehaviour.java");
    private static final Path CREEPER_FUSE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/CreeperFuseBehaviour.java");
    private static final Path ENTITY_HANDLE_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityHandleBridgeBehaviour.java");
    private static final Path ENTITY_TAME_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityTameEventBridgeBehaviour.java");

    @Test
    public void canonicalEntityTargetAndTameFlowsUseCompatBridges() throws IOException {
        assertNoDirectCraftBukkitImports(read(MONSTER_CORE_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitImports(read(GHAST_AI_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitImports(read(PIG_ZOMBIE_AGGRO_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitImports(read(SPIDER_COMBAT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitImports(read(WOLF_COMBAT_INTERACTION_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitImports(read(CREEPER_FUSE_BEHAVIOUR_PATH));

        String entityHandleBridgeText = read(ENTITY_HANDLE_BRIDGE_BEHAVIOUR_PATH);
        String entityTameBridgeText = read(ENTITY_TAME_EVENT_BRIDGE_BEHAVIOUR_PATH);
        Assert.assertTrue(entityHandleBridgeText.contains("import org.bukkit.craftbukkit.entity.CraftEntity;"));
        Assert.assertTrue(entityTameBridgeText.contains("CraftEventFactory.callEntityTameEvent"));
    }

    private static void assertNoDirectCraftBukkitImports(String text) {
        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit."));
        Assert.assertFalse(text.contains("CraftEventFactory."));
        Assert.assertFalse(text.contains("CraftEntity"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
