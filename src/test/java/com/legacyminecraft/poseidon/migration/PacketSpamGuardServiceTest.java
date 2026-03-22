package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketSpamGuardSystem;
import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Test;

public class PacketSpamGuardServiceTest {
    @Test
    public void disabledOrBelowThresholdProducesNoAction() {
        PacketSpamGuardSystem service = PacketSpamGuardSystem.getInstance();

        PacketSpamGuardSystem.SpamDecision disabledDecision = service.evaluate(false, 200, 100, "Alex", true);
        Assert.assertFalse(disabledDecision.shouldKickPlayer());
        Assert.assertFalse(disabledDecision.shouldDisconnectConnection());
        Assert.assertFalse(disabledDecision.shouldLog());

        PacketSpamGuardSystem.SpamDecision belowThresholdDecision = service.evaluate(true, 100, 100, "Alex", true);
        Assert.assertFalse(belowThresholdDecision.shouldKickPlayer());
        Assert.assertFalse(belowThresholdDecision.shouldDisconnectConnection());
        Assert.assertFalse(belowThresholdDecision.shouldLog());
    }

    @Test
    public void playerConnectionTriggersKickDecision() {
        PacketSpamGuardSystem service = PacketSpamGuardSystem.getInstance();
        PacketSpamGuardSystem.SpamDecision decision = service.evaluate(true, 101, 100, "Alex", true);

        Assert.assertTrue(decision.shouldKickPlayer());
        Assert.assertFalse(decision.shouldDisconnectConnection());
        Assert.assertEquals(ChatColor.RED + "[Poseidon] You have been kicked for packet spamming.", decision.getKickReason());
        Assert.assertTrue(decision.shouldLog());
        Assert.assertTrue(decision.getLogMessage().contains("Alex"));
    }

    @Test
    public void nonPlayerConnectionTriggersGenericDisconnectDecision() {
        PacketSpamGuardSystem service = PacketSpamGuardSystem.getInstance();
        PacketSpamGuardSystem.SpamDecision decision = service.evaluate(true, 120, 100, null, false);

        Assert.assertFalse(decision.shouldKickPlayer());
        Assert.assertTrue(decision.shouldDisconnectConnection());
        Assert.assertEquals("disconnect.spam", decision.getDisconnectKey());
        Assert.assertTrue(decision.shouldLog());
        Assert.assertTrue(decision.getLogMessage().contains("Unknown"));
    }
}
