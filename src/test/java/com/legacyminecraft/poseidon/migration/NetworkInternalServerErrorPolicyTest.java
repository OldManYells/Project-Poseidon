package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkInternalServerErrorPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkInternalServerErrorPolicyTest {
    @Test
    public void exposesLegacyInternalServerErrorMessage() {
        Assert.assertEquals(
                "Internal server error",
                NetworkInternalServerErrorPolicy.getInstance().internalServerErrorMessage()
        );
    }
}
