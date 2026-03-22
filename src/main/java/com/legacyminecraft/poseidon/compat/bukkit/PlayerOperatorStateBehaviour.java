package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.ServerConfigurationManager;

/**
 * Canonical behaviour for CraftPlayer operator-state query and update policy.
 */
public final class PlayerOperatorStateBehaviour {
    private static final PlayerOperatorStateBehaviour INSTANCE = new PlayerOperatorStateBehaviour();

    private PlayerOperatorStateBehaviour() {
    }

    public static PlayerOperatorStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOperator(ServerConfigurationManager configurationManager, String playerName) {
        return configurationManager.isOp(playerName);
    }

    public boolean updateOperatorState(ServerConfigurationManager configurationManager, String playerName,
                                       boolean desiredOperatorState, boolean currentOperatorState) {
        if (desiredOperatorState == currentOperatorState) {
            return false;
        }

        if (desiredOperatorState) {
            configurationManager.e(playerName);
        } else {
            configurationManager.f(playerName);
        }
        return true;
    }
}
