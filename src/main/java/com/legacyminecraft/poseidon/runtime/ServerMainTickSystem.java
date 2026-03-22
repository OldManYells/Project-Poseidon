package com.legacyminecraft.poseidon.runtime;

import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Role-aligned canonical system for main-thread server tick execution.
 */
public final class ServerMainTickSystem {
    private static final ServerMainTickSystem INSTANCE = new ServerMainTickSystem();
    private final ServerMainTickService delegate = ServerMainTickService.getInstance();

    private ServerMainTickSystem() {
    }

    public static ServerMainTickSystem getInstance() {
        return INSTANCE;
    }

    public int executeMainTick(
            MinecraftServer server,
            int currentTicks,
            Map trackerList,
            List updateBoxes,
            TickTelemetry tickTelemetry,
            WorldTickOrchestrator worldTickOrchestrator,
            final CommandDrainAction commandDrainAction,
            Logger logger
    ) {
        return delegate.executeMainTick(
                server,
                currentTicks,
                trackerList,
                updateBoxes,
                tickTelemetry,
                worldTickOrchestrator,
                new ServerMainTickService.CommandDrainAction() {
                    @Override
                    public void drainCommands() {
                        commandDrainAction.drainCommands();
                    }
                },
                logger
        );
    }

    public interface CommandDrainAction {
        void drainCommands();
    }
}

