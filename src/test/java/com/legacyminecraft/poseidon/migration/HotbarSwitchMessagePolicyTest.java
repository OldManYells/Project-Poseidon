package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.HotbarSwitchMessagePolicy;
import org.junit.Assert;
import org.junit.Test;

public class HotbarSwitchMessagePolicyTest {
    @Test
    public void exposesLegacyInvalidSelectionKickMessage() {
        Assert.assertEquals(
                "Invalid hotbar selection (Hacking?)",
                HotbarSwitchMessagePolicy.getInstance().invalidSelectionKickMessage()
        );
    }
}
