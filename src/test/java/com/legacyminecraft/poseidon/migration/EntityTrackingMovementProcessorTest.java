package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingMovementProcessor;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class EntityTrackingMovementProcessorTest {
    @Test
    public void viewerRefreshOnlyNeededForPlayerEntities() {
        EntityTrackingMovementProcessor processor = EntityTrackingMovementProcessor.getInstance();

        Assert.assertFalse(processor.shouldRefreshViewerListBeforeTeleport(new NonPlayerEntity()));
    }

    private static final class NonPlayerEntity extends Entity {
        private NonPlayerEntity() {
            super(null);
        }

        @Override
        protected void b() {
        }

        @Override
        protected void a(NBTTagCompound nbttagcompound) {
        }

        @Override
        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
