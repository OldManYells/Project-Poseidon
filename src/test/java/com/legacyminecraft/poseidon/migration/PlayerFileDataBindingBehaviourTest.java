package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerFileDataBindingBehaviour;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.PlayerFileData;
import org.junit.Assert;
import org.junit.Test;

public class PlayerFileDataBindingBehaviourTest {
    private final PlayerFileDataBindingBehaviour playerFileDataBindingBehaviour = PlayerFileDataBindingBehaviour.getInstance();

    @Test
    public void bindIfAbsentReturnsExistingWithoutResolving() {
        PlayerFileData existingPlayerFileData = new NoOpPlayerFileData();
        SupplierCapture supplierCapture = new SupplierCapture(new NoOpPlayerFileData());

        PlayerFileData resolved = playerFileDataBindingBehaviour.bindIfAbsent(existingPlayerFileData, supplierCapture);

        Assert.assertSame(existingPlayerFileData, resolved);
        Assert.assertFalse(supplierCapture.called);
    }

    @Test
    public void bindIfAbsentResolvesWhenMissing() {
        PlayerFileData suppliedPlayerFileData = new NoOpPlayerFileData();
        SupplierCapture supplierCapture = new SupplierCapture(suppliedPlayerFileData);

        PlayerFileData resolved = playerFileDataBindingBehaviour.bindIfAbsent(null, supplierCapture);

        Assert.assertSame(suppliedPlayerFileData, resolved);
        Assert.assertTrue(supplierCapture.called);
    }

    private static final class SupplierCapture implements PlayerFileDataBindingBehaviour.PlayerFileDataSupplier {
        private final PlayerFileData suppliedPlayerFileData;
        private boolean called;

        private SupplierCapture(PlayerFileData suppliedPlayerFileData) {
            this.suppliedPlayerFileData = suppliedPlayerFileData;
        }

        @Override
        public PlayerFileData resolve() {
            this.called = true;
            return suppliedPlayerFileData;
        }
    }

    private static final class NoOpPlayerFileData implements PlayerFileData {
        @Override
        public void a(EntityHuman entityhuman) {
        }

        @Override
        public void b(EntityHuman entityhuman) {
        }
    }
}
