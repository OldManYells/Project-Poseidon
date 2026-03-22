package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.RedstoneOreStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RedstoneOreStateServiceTest {
    @Test
    public void updateDropAndToggleRulesMatchLegacyBehavior() {
        RedstoneOreStateBehaviour service = RedstoneOreStateBehaviour.getInstance();
        Random seeded = new Random(4L);

        Assert.assertEquals(30, service.updateDelayTicks());
        Assert.assertEquals(331, service.resolveDropItemId(331));
        int dropCount = service.resolveDropCount(seeded);
        Assert.assertTrue(dropCount >= 4);
        Assert.assertTrue(dropCount <= 5);
        Assert.assertTrue(service.shouldSwitchToGlowing(73, 73));
        Assert.assertFalse(service.shouldSwitchToGlowing(74, 73));
        Assert.assertTrue(service.shouldRevertToNormal(74, 74));
        Assert.assertFalse(service.shouldRevertToNormal(73, 74));
    }

    @Test
    public void particleEmissionMatchesLegacyOcclusionBehavior() {
        RedstoneOreStateBehaviour service = RedstoneOreStateBehaviour.getInstance();
        AtomicInteger emitted = new AtomicInteger();
        Random seeded = new Random(3L);

        service.emitActivationParticles(
                new RedstoneOreStateBehaviour.ParticleEmitter() {
                    public void emit(String particle, double x, double y, double z) {
                        emitted.incrementAndGet();
                    }
                },
                new RedstoneOreStateBehaviour.OcclusionQuery() {
                    public boolean isOccluding(int x, int y, int z) {
                        return false;
                    }
                },
                seeded,
                10,
                0,
                10
        );
        Assert.assertEquals(6, emitted.get());

        AtomicInteger emittedWhenOccluded = new AtomicInteger();
        service.emitActivationParticles(
                new RedstoneOreStateBehaviour.ParticleEmitter() {
                    public void emit(String particle, double x, double y, double z) {
                        emittedWhenOccluded.incrementAndGet();
                    }
                },
                new RedstoneOreStateBehaviour.OcclusionQuery() {
                    public boolean isOccluding(int x, int y, int z) {
                        return true;
                    }
                },
                seeded,
                10,
                0,
                10
        );
        Assert.assertEquals(0, emittedWhenOccluded.get());
    }
}
