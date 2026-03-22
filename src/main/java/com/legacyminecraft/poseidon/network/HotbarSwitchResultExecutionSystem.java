package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.inventory.HotbarSelectionBehaviour;

import java.util.logging.Logger;

/**
 * Canonical execution flow for hotbar selection packet outcomes.
 */
public final class HotbarSwitchResultExecutionSystem {
    private static final HotbarSwitchResultExecutionSystem INSTANCE = new HotbarSwitchResultExecutionSystem();
    private static final String INVALID_SWITCH_KICK_MESSAGE = "Invalid hotbar selection (Hacking?)";

    private HotbarSwitchResultExecutionSystem() {
    }

    public static HotbarSwitchResultExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean executeResult(
            HotbarSelectionBehaviour.SwitchResult switchResult,
            String playerName,
            Logger logger,
            SwitchActions switchActions
    ) {
        if (switchResult == HotbarSelectionBehaviour.SwitchResult.INVALID_SELECTION) {
            logger.warning(playerName + " tried to set an invalid carried item");
            switchActions.disconnect(INVALID_SWITCH_KICK_MESSAGE);
            return false;
        }
        return true;
    }

    public interface SwitchActions {
        void disconnect(String message);
    }
}
