package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkSocketCloseSuppressionPolicy;
import org.junit.Test;

public class NetworkSocketCloseSuppressionPolicyTest {
    @Test
    public void suppressIsNoOp() {
        NetworkSocketCloseSuppressionPolicy.getInstance().suppress(new RuntimeException("ignored"));
    }
}
