package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerStatisticPacketSystem;
import net.minecraft.server.Statistic;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PlayerStatisticPacketServiceTest {
    @Test
    public void splitIntoPacketAmountsBatchesAtHundred() {
        PlayerStatisticPacketSystem service = PlayerStatisticPacketSystem.getInstance();
        List<Integer> packetAmounts = service.splitIntoPacketAmounts(250, 100);

        Assert.assertEquals(3, packetAmounts.size());
        Assert.assertEquals(Integer.valueOf(100), packetAmounts.get(0));
        Assert.assertEquals(Integer.valueOf(100), packetAmounts.get(1));
        Assert.assertEquals(Integer.valueOf(50), packetAmounts.get(2));
    }

    @Test
    public void shouldSendStatisticSkipsNullAndMarkedStats() {
        PlayerStatisticPacketSystem service = PlayerStatisticPacketSystem.getInstance();
        Statistic hiddenStatistic = new Statistic(1, "hidden").e();
        Statistic visibleStatistic = new Statistic(2, "visible");

        Assert.assertFalse(service.shouldSendStatistic(null));
        Assert.assertFalse(service.shouldSendStatistic(hiddenStatistic));
        Assert.assertTrue(service.shouldSendStatistic(visibleStatistic));
    }
}
