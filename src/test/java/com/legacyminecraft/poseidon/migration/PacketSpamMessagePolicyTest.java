package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketSpamMessagePolicy;
import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Test;

public class PacketSpamMessagePolicyTest {
    @Test
    public void exposesLegacyPacketSpamMessages() {
        PacketSpamMessagePolicy policy = PacketSpamMessagePolicy.getInstance();

        Assert.assertEquals("Unknown", policy.unknownUsername());
        Assert.assertEquals(
                ChatColor.RED + "[Poseidon] You have been kicked for packet spamming.",
                policy.spamKickReason()
        );
    }
}
