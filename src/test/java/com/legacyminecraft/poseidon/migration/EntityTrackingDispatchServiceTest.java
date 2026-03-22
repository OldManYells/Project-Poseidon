package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingDispatchSystem;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class EntityTrackingDispatchServiceTest {
    @Test
    public void emptyTrackedSetOperationsAreNoOps() {
        EntityTrackingDispatchSystem service = EntityTrackingDispatchSystem.getInstance();
        Set trackedPlayers = new HashSet();

        service.sendToTrackedPlayers(trackedPlayers, new Packet0KeepAlive());
        service.sendToTrackedPlayersAndSelf(trackedPlayers, new NonPlayerEntity(), new Packet0KeepAlive());
        service.queueDestroyForTrackedPlayers(trackedPlayers, 42);
        service.removeTrackedPlayer(trackedPlayers, null, 42);
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
