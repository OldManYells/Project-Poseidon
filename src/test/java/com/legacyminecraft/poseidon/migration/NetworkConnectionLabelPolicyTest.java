package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkConnectionLabelPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkConnectionLabelPolicyTest {
    @Test
    public void buildsLegacyConnectionLabels() {
        Assert.assertEquals("Connection #42", NetworkConnectionLabelPolicy.getInstance().buildConnectionLabel(42));
    }
}
