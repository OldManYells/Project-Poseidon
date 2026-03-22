package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.StatisticTranslationBehaviour;
import net.minecraft.server.StatisticCollector;
import net.minecraft.server.StatisticStorage;
import org.junit.Assert;
import org.junit.Test;

public class StatisticTranslationServiceTest {
    private final StatisticTranslationBehaviour service = StatisticTranslationBehaviour.getInstance();

    @Test
    public void translateResolvesKnownStatisticKey() {
        Assert.assertEquals("Times played", service.translate("stat.startGame"));
    }

    @Test
    public void translateFallsBackToOriginalKeyWhenMissing() {
        Assert.assertEquals("poseidon.unknown.key", service.translate("poseidon.unknown.key"));
    }

    @Test
    public void formatInterpolatesArgumentsForAchievementMessage() {
        Assert.assertEquals("Requires 'Benchmarking'", service.format("achievement.requires", "Benchmarking"));
    }

    @Test
    public void legacyStorageAndCollectorDelegateToCanonicalService() {
        Assert.assertEquals(service.translate("stat.startGame"), StatisticStorage.a().a("stat.startGame"));
        Assert.assertEquals(service.translate("stat.startGame"), StatisticCollector.a("stat.startGame"));
        Assert.assertEquals(
                service.format("achievement.requires", "Time to Mine!"),
                StatisticCollector.a("achievement.requires", "Time to Mine!")
        );
    }
}
