package com.legacyminecraft.poseidon.network;

/**
 * Canonical message policy for hotbar switch validation failures.
 */
public final class HotbarSwitchMessagePolicy {
    private static final HotbarSwitchMessagePolicy INSTANCE = new HotbarSwitchMessagePolicy();
    private static final String INVALID_SELECTION_KICK_MESSAGE = "Invalid hotbar selection (Hacking?)";

    private HotbarSwitchMessagePolicy() {
    }

    public static HotbarSwitchMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String invalidSelectionKickMessage() {
        return INVALID_SELECTION_KICK_MESSAGE;
    }
}
