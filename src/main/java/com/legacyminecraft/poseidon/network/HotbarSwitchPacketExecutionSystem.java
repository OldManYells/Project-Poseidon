package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.inventory.HotbarSelectionBehaviour;

import java.util.logging.Logger;

/**
 * Canonical orchestration for hotbar-switch packet handling and outcome execution.
 */
public final class HotbarSwitchPacketExecutionSystem {
    private static final HotbarSwitchPacketExecutionSystem INSTANCE = new HotbarSwitchPacketExecutionSystem();

    private HotbarSwitchPacketExecutionSystem() {
    }

    public static HotbarSwitchPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean execute(
            SwitchResolver switchResolver,
            HotbarSwitchResultExecutionSystem hotbarSwitchResultExecutionSystem,
            String playerName,
            Logger logger,
            HotbarSwitchResultExecutionSystem.SwitchActions switchActions
    ) {
        HotbarSelectionBehaviour.SwitchResult switchResult = switchResolver.resolve();
        return hotbarSwitchResultExecutionSystem.executeResult(switchResult, playerName, logger, switchActions);
    }

    public interface SwitchResolver {
        HotbarSelectionBehaviour.SwitchResult resolve();
    }
}
