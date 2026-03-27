package com.legacyminecraft.poseidon.runtime;


import java.util.List;

/**
 * Canonical execution flow for registering player-list tick listeners.
 */
public final class PlayerListTickRegistrationSystem {
    private static final PlayerListTickRegistrationSystem INSTANCE = new PlayerListTickRegistrationSystem();

    private PlayerListTickRegistrationSystem() {
    }

    public static PlayerListTickRegistrationSystem getInstance() {
        return INSTANCE;
    }

    public void register(List playerListTickListeners, IUpdatePlayerListBox updatePlayerListBox) {
        playerListTickListeners.add(updatePlayerListBox);
    }
}
