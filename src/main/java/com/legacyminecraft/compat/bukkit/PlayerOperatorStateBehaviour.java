package com.legacyminecraft.compat.bukkit;


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

    public boolean isOperator(Object configurationManager, String playerName) {
        return (Boolean) BridgeReflection.invoke(configurationManager, "isOp", playerName);
    }

    public boolean updateOperatorState(Object configurationManager, String playerName,
                                       boolean desiredOperatorState, boolean currentOperatorState) {
        if (desiredOperatorState == currentOperatorState) {
            return false;
        }

        if (desiredOperatorState) {
            BridgeReflection.invoke(configurationManager, "e", playerName);
        } else {
            BridgeReflection.invoke(configurationManager, "f", playerName);
        }
        return true;
    }

    public void recalculatePermissionsIfChanged(Object permissibleBase, boolean changed) {
        if (changed) {
            BridgeReflection.invoke(permissibleBase, "recalculatePermissions");
        }
    }
}
