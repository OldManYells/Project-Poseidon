package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PortalFrameBehaviour;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class PortalFrameServiceTest {
    @Test
    public void axisBoundsAndValidationRulesMatchLegacyPortalBehavior() {
        PortalFrameBehaviour service = PortalFrameBehaviour.getInstance();

        PortalFrameBehaviour.PortalAxis xAxis = service.resolveCreationAxis(true, false, false, false);
        Assert.assertTrue(xAxis.valid);
        Assert.assertEquals(1, xAxis.axisX);
        Assert.assertEquals(0, xAxis.axisZ);

        PortalFrameBehaviour.PortalAxis invalidAxis = service.resolveCreationAxis(true, false, true, false);
        Assert.assertFalse(invalidAxis.valid);

        PortalFrameBehaviour.Bounds zBounds = service.resolvePortalBounds(false, false);
        Assert.assertEquals(0.375F, zBounds.minX, 0.0F);
        Assert.assertEquals(0.0F, zBounds.minY, 0.0F);
        PortalFrameBehaviour.Bounds xBounds = service.resolvePortalBounds(true, false);
        Assert.assertEquals(0.0F, xBounds.minX, 0.0F);
        Assert.assertEquals(0.375F, xBounds.minZ, 0.0F);

        Assert.assertTrue(service.shouldShiftOriginToLowerLeft(0));
        Assert.assertFalse(service.shouldShiftOriginToLowerLeft(1));
        Assert.assertTrue(service.shouldInspectFrameCoordinate(0, 0));
        Assert.assertFalse(service.shouldInspectFrameCoordinate(-1, -1));
        Assert.assertTrue(service.isFrameBoundaryCoordinate(-1, 2));
        Assert.assertFalse(service.isFrameBoundaryCoordinate(0, 1));
        Assert.assertTrue(service.isValidFrameBoundaryBlock(49, 49));
        Assert.assertTrue(service.isValidPortalInteriorBlock(0, 51));
        Assert.assertTrue(service.isValidPortalInteriorBlock(51, 51));
        Assert.assertFalse(service.isValidPortalInteriorBlock(1, 51));
    }

    @Test
    public void stabilityCoordinatesAndEntityTriggerRulesMatchLegacyPortalBehavior() {
        PortalFrameBehaviour service = PortalFrameBehaviour.getInstance();

        int baseY = service.findPortalBaseY(new PortalFrameBehaviour.TypeIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return y >= 63 && y <= 64 ? 90 : 0;
            }
        }, 10, 64, 10, 90);
        Assert.assertEquals(63, baseY);
        Assert.assertTrue(service.hasValidPortalBase(49, 49));
        Assert.assertEquals(3, service.countVerticalPortalSpan(new PortalFrameBehaviour.TypeIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return y >= 64 && y <= 66 ? 90 : 0;
            }
        }, 10, 64, 10, 90));
        Assert.assertTrue(service.hasValidPortalCap(3, 49, 49));
        Assert.assertTrue(service.hasCrossAxisPortalConflict(true, true));
        Assert.assertTrue(service.hasValidSideSupportPair(49, 90, 49, 90));
        Assert.assertTrue(service.shouldDropPortalForInvalidSideSupport(false));
        Assert.assertEquals(0, service.noDropCount());

        AtomicInteger frameCount = new AtomicInteger();
        service.forEachPortalFrameCoordinate(10, 64, 10, 1, 0, new PortalFrameBehaviour.CoordinateConsumer() {
            public void accept(int x, int y, int z) {
                frameCount.incrementAndGet();
            }
        });
        Assert.assertEquals(10, frameCount.get());

        AtomicInteger interiorCount = new AtomicInteger();
        service.forEachPortalInteriorCoordinate(10, 64, 10, 1, 0, new PortalFrameBehaviour.CoordinateConsumer() {
            public void accept(int x, int y, int z) {
                interiorCount.incrementAndGet();
            }
        });
        Assert.assertEquals(6, interiorCount.get());

        DummyEntity entity = new DummyEntity();
        Assert.assertTrue(service.shouldTriggerEntityPortal(entity));
        entity.passenger = new DummyEntity();
        Assert.assertFalse(service.shouldTriggerEntityPortal(entity));
    }

    private static final class DummyEntity extends Entity {
        private DummyEntity() {
            super((World) null);
        }

        protected void b() {
        }

        protected void a(NBTTagCompound nbttagcompound) {
        }

        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
