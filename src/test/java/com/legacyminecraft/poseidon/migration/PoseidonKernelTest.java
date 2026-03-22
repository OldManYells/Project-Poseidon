package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.kernel.PoseidonKernel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PoseidonKernelTest {
    private final PoseidonKernel kernel = PoseidonKernel.getInstance();

    @After
    public void resetKernel() {
        kernel.clear();
    }

    @Test
    public void registersAndReturnsServices() {
        kernel.registerService(String.class, "ok");
        Assert.assertTrue(kernel.findService(String.class).isPresent());
        Assert.assertEquals("ok", kernel.getRequiredService(String.class));
    }

    @Test
    public void tracksBootstrapState() {
        Assert.assertFalse(kernel.isBootstrapped());
        kernel.markBootstrapped();
        Assert.assertTrue(kernel.isBootstrapped());
    }
}
