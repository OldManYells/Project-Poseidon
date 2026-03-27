package com.legacyminecraft.compat.bukkit;


/**
 * Canonical policy for validating CraftEventFactory air-interaction actions.
 */
public final class PlayerInteractActionValidationPolicy {
    private static final PlayerInteractActionValidationPolicy INSTANCE = new PlayerInteractActionValidationPolicy();

    private PlayerInteractActionValidationPolicy() {
    }

    public static PlayerInteractActionValidationPolicy getInstance() {
        return INSTANCE;
    }

    public void validateAirInteractionAction(Action action) {
        if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR) {
            throw new IllegalArgumentException();
        }
    }
}
