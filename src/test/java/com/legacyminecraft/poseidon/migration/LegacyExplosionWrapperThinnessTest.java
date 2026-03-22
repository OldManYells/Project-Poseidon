package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyExplosionWrapperThinnessTest {
    private static final Path EXPLOSION_PATH = Paths.get("src/main/java/net/minecraft/server/Explosion.java");

    @Test
    public void explosionDelegatesDamageIgnitionAndParticleMathToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(EXPLOSION_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ExplosionEffectBehaviour"));
        Assert.assertTrue(text.contains("ExplosionEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("ExplosionBlockDestructionBehaviour"));
        Assert.assertTrue(text.contains("ExplosionEntityImpactSystem"));
        Assert.assertTrue(text.contains("ExplosionRaycastBehaviour"));
        Assert.assertTrue(text.contains("EXPLOSION_ENTITY_IMPACT_SYSTEM.applyImpacts"));
        Assert.assertTrue(text.contains("EXPLOSION_EFFECT_BEHAVIOUR.shouldIgniteBlock"));
        Assert.assertTrue(text.contains("EXPLOSION_EFFECT_BEHAVIOUR.computeParticleImpulse"));
        Assert.assertTrue(text.contains("EXPLOSION_RAYCAST_BEHAVIOUR.collectAffectedBlocks"));
        Assert.assertTrue(text.contains("EXPLOSION_EVENT_BRIDGE_BEHAVIOUR.callExplosionEvent"));
        Assert.assertTrue(text.contains("EXPLOSION_BLOCK_DESTRUCTION_BEHAVIOUR.shouldDestroyBlock"));
        Assert.assertTrue(text.contains("EXPLOSION_BLOCK_DESTRUCTION_BEHAVIOUR.destroyAndDrop"));
        Assert.assertFalse(text.contains("(int) ((d10 * d10 + d10) / 2.0D * 8.0D * (double) this.size + 1.0D)"));
        Assert.assertFalse(text.contains("if (l3 == 0 && Block.o[i4] && this.random.nextInt(3) == 0)"));
        Assert.assertFalse(text.contains("double d7 = 0.5D / (d6 / (double) this.size + 0.1D);"));
        Assert.assertFalse(text.contains("private EntityDamageByBlockEvent getEntityDamageByBlockEvent"));
        Assert.assertFalse(text.contains("EntityExplodeEvent event = new EntityExplodeEvent"));
        Assert.assertFalse(text.contains("new EntityDamageByEntityEvent"));
        Assert.assertFalse(text.contains("Block.byId[i1].dropNaturally(this.world, j, k, l, this.world.getData(j, k, l), explosionYield);"));
        Assert.assertFalse(text.contains("for (int k2 = 0; k2 < list.size(); ++k2)"));
        Assert.assertFalse(text.contains("for (i = 0; i < b0; ++i)"));
        Assert.assertFalse(text.contains("entity.motX += d0 * d10;"));
        Assert.assertFalse(text.contains("private float getBlockDensity(Vec3D vec3d, Entity entity)"));
        Assert.assertFalse(text.contains("static class CacheKey"));
    }
}
