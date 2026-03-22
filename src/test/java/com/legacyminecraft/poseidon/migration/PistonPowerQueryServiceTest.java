package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonPowerQueryBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class PistonPowerQueryServiceTest {
    @Test
    public void returnsFalseWhenNoPowerSourcesArePresent() {
        PistonPowerQueryBehaviour service = PistonPowerQueryBehaviour.getInstance();

        Assert.assertFalse(service.isIndirectlyPoweredExceptFacing(new StubPowerQuery(), 10, 64, 10, 2));
    }

    @Test
    public void respectsExcludedFacingForDirectNeighborChecks() {
        PistonPowerQueryBehaviour service = PistonPowerQueryBehaviour.getInstance();
        StubPowerQuery query = new StubPowerQuery();
        query.add(10, 63, 10, 0);

        Assert.assertFalse(service.isIndirectlyPoweredExceptFacing(query, 10, 64, 10, 0));
        Assert.assertTrue(service.isIndirectlyPoweredExceptFacing(query, 10, 64, 10, 1));
    }

    @Test
    public void acceptsIndirectTopAndAdjacentPowerChecks() {
        PistonPowerQueryBehaviour service = PistonPowerQueryBehaviour.getInstance();
        StubPowerQuery query = new StubPowerQuery();
        query.add(10, 66, 10, 1);

        Assert.assertTrue(service.isIndirectlyPoweredExceptFacing(query, 10, 64, 10, 4));
    }

    @Test
    public void acceptsHorizontalNeighborPowerWhenFacingIsNotExcluded() {
        PistonPowerQueryBehaviour service = PistonPowerQueryBehaviour.getInstance();
        StubPowerQuery query = new StubPowerQuery();
        query.add(11, 64, 10, 5);

        Assert.assertTrue(service.isIndirectlyPoweredExceptFacing(query, 10, 64, 10, 0));
        Assert.assertFalse(service.isIndirectlyPoweredExceptFacing(query, 10, 64, 10, 5));
    }

    private static final class StubPowerQuery implements PistonPowerQueryBehaviour.IndirectPowerQuery {
        private final Set poweredKeys = new HashSet();

        public void add(int x, int y, int z, int face) {
            poweredKeys.add(key(x, y, z, face));
        }

        public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
            return poweredKeys.contains(key(x, y, z, face));
        }

        private String key(int x, int y, int z, int face) {
            return x + ":" + y + ":" + z + ":" + face;
        }
    }
}
