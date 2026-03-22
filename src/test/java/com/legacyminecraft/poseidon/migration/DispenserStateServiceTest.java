package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.DispenserStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class DispenserStateServiceTest {
    @Test
    public void facingTextureAndSchedulingRulesMatchLegacyBehavior() {
        DispenserStateBehaviour service = DispenserStateBehaviour.getInstance();

        Assert.assertEquals(4, service.resolveTickRate());
        Assert.assertEquals(23, service.resolveDroppedBlockId(23));
        Assert.assertEquals(3, service.resolveDefaultFacing(true, false, false, false));
        Assert.assertEquals(2, service.resolveDefaultFacing(false, true, false, false));
        Assert.assertEquals(5, service.resolveDefaultFacing(false, false, true, false));
        Assert.assertEquals(4, service.resolveDefaultFacing(false, false, false, true));
        Assert.assertEquals(62, service.resolveTextureBySide(1, 45));
        Assert.assertEquals(46, service.resolveTextureBySide(3, 45));
        Assert.assertEquals(45, service.resolveTextureBySide(4, 45));

        DispenserStateBehaviour.Facing south = service.resolveDispenseFacing(3);
        Assert.assertEquals(0, south.offsetX);
        Assert.assertEquals(1, south.offsetZ);
        DispenserStateBehaviour.Facing north = service.resolveDispenseFacing(2);
        Assert.assertEquals(0, north.offsetX);
        Assert.assertEquals(-1, north.offsetZ);
        DispenserStateBehaviour.Facing east = service.resolveDispenseFacing(5);
        Assert.assertEquals(1, east.offsetX);
        Assert.assertEquals(0, east.offsetZ);
        DispenserStateBehaviour.Facing west = service.resolveDispenseFacing(4);
        Assert.assertEquals(-1, west.offsetX);
        Assert.assertEquals(0, west.offsetZ);

        Assert.assertTrue(service.shouldScheduleDispense(true, true, false));
        Assert.assertTrue(service.shouldScheduleDispense(true, false, true));
        Assert.assertFalse(service.shouldScheduleDispense(false, true, true));
        Assert.assertTrue(service.shouldDispenseNow(true, false));
        Assert.assertTrue(service.shouldDispenseNow(false, true));
        Assert.assertFalse(service.shouldDispenseNow(false, false));
    }

    @Test
    public void dispenseVectorsPlacementAndDropScatterRulesMatchLegacyBehavior() {
        DispenserStateBehaviour service = DispenserStateBehaviour.getInstance();

        Assert.assertEquals(10.5D, service.resolveDispenseOriginX(10, 0), 0.0D);
        Assert.assertEquals(10.5D + 0.6D, service.resolveDispenseOriginX(10, 1), 0.0D);
        Assert.assertEquals(64.5D, service.resolveDispenseOriginY(64), 0.0D);
        Assert.assertEquals(10.5D - 0.6D, service.resolveDispenseOriginZ(10, -1), 0.0D);
        Assert.assertEquals(0.2D, service.resolveDispenseBaseSpeed(new FixedRandom(0.0D, 0.0F, 0.0D, 0, 0.0D)), 0.0D);
        Assert.assertEquals(0.2D, service.resolveDispenseVelocityComponent(1, 0.2D, 0.0D), 0.0D);
        Assert.assertEquals(0.20000000298023224D, service.resolveDispenseVerticalVelocity(0.20000000298023224D, 0.0D), 0.0D);
        Assert.assertEquals(8, service.resolveDispenseEventData(1, 1));

        Assert.assertEquals(2, service.resolvePostPlaceData(0.0F));
        Assert.assertEquals(5, service.resolvePostPlaceData(90.0F));
        Assert.assertEquals(3, service.resolvePostPlaceData(180.0F));
        Assert.assertEquals(4, service.resolvePostPlaceData(270.0F));

        FixedRandom random = new FixedRandom(0.0D, 0.0F, 0.0D, 20, 0.0D);
        Assert.assertEquals(0.1F, service.resolveDropOffset(random), 0.0F);
        Assert.assertEquals(5, service.resolveDropStackChunk(random, 5));
        Assert.assertEquals(30, service.resolveDropStackChunk(random, 40));
        Assert.assertEquals(0.0D, service.resolveDropMotion(random, false), 0.0D);
        Assert.assertEquals(0.20000000298023224D, service.resolveDropMotion(random, true), 0.0D);
    }

    private static final class FixedRandom extends Random {
        private final double nextDouble;
        private final float nextFloat;
        private final double nextGaussian;
        private final int nextInt;
        private final double unused;

        private FixedRandom(double nextDouble, float nextFloat, double nextGaussian, int nextInt, double unused) {
            this.nextDouble = nextDouble;
            this.nextFloat = nextFloat;
            this.nextGaussian = nextGaussian;
            this.nextInt = nextInt;
            this.unused = unused;
        }

        public double nextDouble() {
            return nextDouble;
        }

        public float nextFloat() {
            return nextFloat;
        }

        public double nextGaussian() {
            return nextGaussian;
        }

        public int nextInt(int bound) {
            return nextInt;
        }
    }
}
