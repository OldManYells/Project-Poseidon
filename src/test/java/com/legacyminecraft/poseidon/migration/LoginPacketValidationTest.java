package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginPacketValidation;
import org.junit.Assert;
import org.junit.Test;

public class LoginPacketValidationTest {
    @Test
    public void detectsDuplicateLoginPacketState() {
        Assert.assertTrue(LoginPacketValidation.isDuplicateLoginPacket(true));
        Assert.assertFalse(LoginPacketValidation.isDuplicateLoginPacket(false));
    }

    @Test
    public void resolvesProtocolKickMessages() {
        Assert.assertNull(LoginPacketValidation.getProtocolVersionKickMessage(14));
        Assert.assertEquals("Outdated server! I'm still on Beta 1.7.3", LoginPacketValidation.getProtocolVersionKickMessage(15));
        Assert.assertEquals("Outdated client! Please use Beta 1.7.3", LoginPacketValidation.getProtocolVersionKickMessage(13));
    }
}
