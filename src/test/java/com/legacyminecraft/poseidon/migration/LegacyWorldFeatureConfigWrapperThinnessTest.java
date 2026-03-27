package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldFeatureConfigWrapperThinnessTest {
    private static final Path BLOCK_FLOWING_PATH = Paths.get("src/main/java/net/minecraft/server/BlockFlowing.java");
    private static final Path BLOCK_SAND_PATH = Paths.get("src/main/java/net/minecraft/server/BlockSand.java");
    private static final Path BLOCK_PISTON_PATH = Paths.get("src/main/java/net/minecraft/server/BlockPiston.java");
    private static final Path EXPLOSION_PATH = Paths.get("src/main/java/net/minecraft/server/Explosion.java");
    private static final Path ENTITY_PLAYER_PATH = Paths.get("src/main/java/net/minecraft/server/EntityPlayer.java");
    private static final Path BLOCK_SPONGE_PATH = Paths.get("src/main/java/net/minecraft/server/BlockSponge.java");
    private static final Path BLOCK_TORCH_PATH = Paths.get("src/main/java/net/minecraft/server/BlockTorch.java");
    private static final Path TILE_SPAWNER_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntityMobSpawner.java");
    private static final Path CHUNK_PATH = Paths.get("src/main/java/net/minecraft/server/Chunk.java");
    private static final Path CHUNK_PROVIDER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkProviderServer.java");

    @Test
    public void wrappersDelegateWorldFeatureConfigOwnershipToCanonicalPolicy() throws IOException {
        assertUsesPolicy(BLOCK_FLOWING_PATH, "WORLD_FEATURE_CONFIG_POLICY.flowingLavaFixEnabledKey()");
        assertUsesPolicy(BLOCK_SAND_PATH, "WORLD_FEATURE_CONFIG_POLICY.pistonSandGravelDupingFixEnabledKey()");
        assertUsesPolicy(BLOCK_PISTON_PATH, "WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledKey()");
        assertUsesPolicy(EXPLOSION_PATH, "WORLD_FEATURE_CONFIG_POLICY.optimizedExplosionsKey()");
        assertUsesPolicy(ENTITY_PLAYER_PATH, "WORLD_FEATURE_CONFIG_POLICY.randomizeSpawnKey()");
        assertUsesPolicy(BLOCK_SPONGE_PATH, "WORLD_FEATURE_CONFIG_POLICY.optimizeSpongeRemovalKey()");
        assertUsesPolicy(BLOCK_TORCH_PATH, "WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledKey()");
        assertUsesPolicy(TILE_SPAWNER_PATH, "WORLD_FEATURE_CONFIG_POLICY.mobSpawnerAreaLimitEnabledKey()");
        assertUsesPolicy(CHUNK_PATH, "WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledKey()");
        assertUsesPolicy(CHUNK_PROVIDER_PATH, "WORLD_FEATURE_CONFIG_POLICY.regenerateCorruptChunksEnabledKey()");
    }

    @Test
    public void wrappersDoNotRetainInlineWorldFeatureConfigLiterals() throws IOException {
        assertNoInlineLiteral(BLOCK_FLOWING_PATH, "world.settings.flowing-lava-fix.enabled");
        assertNoInlineLiteral(BLOCK_SAND_PATH, "world.settings.pistons.sand-gravel-duping-fix.enabled");
        assertNoInlineLiteral(BLOCK_PISTON_PATH, "world.settings.pistons.other-fixes.enabled");
        assertNoInlineLiteral(EXPLOSION_PATH, "world-settings.optimized-explosions");
        assertNoInlineLiteral(EXPLOSION_PATH, "world-settings.send-explosion-velocity");
        assertNoInlineLiteral(ENTITY_PLAYER_PATH, "world-settings.randomize-spawn");
        assertNoInlineLiteral(BLOCK_SPONGE_PATH, "fix.optimize-sponges.enabled");
        assertNoInlineLiteral(BLOCK_TORCH_PATH, "world.settings.pistons.transmutation-fix.enabled");
        assertNoInlineLiteral(BLOCK_TORCH_PATH, "world.settings.pistons.other-fixes.enabled");
        assertNoInlineLiteral(TILE_SPAWNER_PATH, "world.settings.mob-spawner-area-limit.enable");
        assertNoInlineLiteral(TILE_SPAWNER_PATH, "world.settings.mob-spawner-area-limit.limit");
        assertNoInlineLiteral(TILE_SPAWNER_PATH, "world.settings.mob-spawner-area-limit.chunk-radius");
        assertNoInlineLiteral(CHUNK_PATH, "world.settings.pistons.transmutation-fix.enabled");
        assertNoInlineLiteral(CHUNK_PROVIDER_PATH, "emergency.debug.regenerate-corrupt-chunks.enable");
    }

    private static void assertUsesPolicy(Path path, String snippet) throws IOException {
        String text = read(path);
        Assert.assertTrue(path + " should contain " + snippet, text.contains("WorldFeatureConfigPolicy"));
        Assert.assertTrue(path + " should contain " + snippet, text.contains(snippet));
    }

    private static void assertNoInlineLiteral(Path path, String literal) throws IOException {
        String text = read(path);
        Assert.assertFalse(path + " should not contain inline literal: " + literal, text.contains(literal));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
