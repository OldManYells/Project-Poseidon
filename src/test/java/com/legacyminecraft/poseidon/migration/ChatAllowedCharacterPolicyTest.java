package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ChatAllowedCharacterPolicy;
import net.minecraft.server.FontAllowedCharacters;
import org.junit.Assert;
import org.junit.Test;

public class ChatAllowedCharacterPolicyTest {
    @Test
    public void exposesNmsAllowedCharacterSet() {
        ChatAllowedCharacterPolicy policy = ChatAllowedCharacterPolicy.getInstance();

        Assert.assertEquals(FontAllowedCharacters.allowedCharacters, policy.allowedCharacters());
    }
}
