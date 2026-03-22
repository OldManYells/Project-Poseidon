package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.compat.bukkit.SchedulerHeartbeatBridge;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Vec3D;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical main-thread tick body for legacy MinecraftServer wrappers.
 */
public final class ServerMainTickService {
    private static final ServerMainTickService INSTANCE = new ServerMainTickService();

    private final TrackerListMaintenance trackerListMaintenance = TrackerListMaintenance.getInstance();
    private final SchedulerHeartbeatBridge schedulerHeartbeatBridge = SchedulerHeartbeatBridge.getInstance();

    private ServerMainTickService() {
    }

    public static ServerMainTickService getInstance() {
        return INSTANCE;
    }

    public int executeMainTick(
            MinecraftServer server,
            int currentTicks,
            Map trackerList,
            List updateBoxes,
            TickTelemetry tickTelemetry,
            WorldTickOrchestrator worldTickOrchestrator,
            CommandDrainAction commandDrainAction,
            Logger logger
    ) {
        trackerListMaintenance.expireTrackerEntries(trackerList);

        AxisAlignedBB.a();
        Vec3D.a();
        int nextTicks = currentTicks + 1;
        schedulerHeartbeatBridge.runMainThreadHeartbeat(server.server.getScheduler(), nextTicks);
        tickTelemetry.recordTick();

        worldTickOrchestrator.tickWorlds(server, nextTicks);
        worldTickOrchestrator.flushNetworkAndPlayerChunks(server);
        worldTickOrchestrator.updateWorldTrackers(server);
        worldTickOrchestrator.tickUpdateBoxes(updateBoxes);

        try {
            commandDrainAction.drainCommands();
        } catch (Exception exception) {
            logger.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
        }

        return nextTicks;
    }

    public interface CommandDrainAction {
        void drainCommands();
    }
}
